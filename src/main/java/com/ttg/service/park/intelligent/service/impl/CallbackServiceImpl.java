package com.ttg.service.park.intelligent.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.utils.util.ParkApiUtil;
import com.ttg.service.park.dto.api.FreeDetailDto;
import com.ttg.service.park.dto.api.SyncOrderDto;
import com.ttg.service.park.intelligent.entity.PCOrder;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.ttg.service.park.intelligent.mapper.PCOrderMapper;
import com.ttg.service.park.intelligent.mapper.PayOrderMapper;
import com.ttg.service.park.intelligent.service.ICallbackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>Title: CallbackServiceImpl</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-29 16:38
 */
@Service
@Transactional
public class CallbackServiceImpl implements ICallbackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ICallbackService.class);

    @Autowired
    private PCOrderMapper orderMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    /**
     * 保存交易结果
     *
     * @param out_no
     * @param status
     * @param ord_no
     */
    @Override
    public void savePayBack(String out_no, String status, String ord_no, Long timestamp) {
        //先查看订单
        PCOrder order = orderMapper.selectByOrderId(out_no);
        if (!Optional.fromNullable(out_no).isPresent()) {
            throw new BusiException("无法找到对应的订单记录信息");
        }
        //校验生成的缴费明细单是否生成
        System.out.println(order.getMerId() + ":" + order.getOrderId() + ":" + order.getScode());
        PayOrder itemDetail = payOrderMapper.findDetailInfo(order.getOrderId());
        //订单状态  1支付成功 2 未支付  4取消支付
        //支付宝回调状态  1位支付成功  4为取消支付
        //订单是否已经成功
        if (Objects.equal(status, "1")) {
            if (order.getOrderStatus() != 1) {
                order.setOrderStatus(1);
                order.setPOrderId(ord_no);
                order.setPayId(1);
                order.setPayTime(new Date());
                orderMapper.updateById(order);
                if (itemDetail != null && StringUtils.isNotBlank(itemDetail.getOrderNo())) {
                    itemDetail.setOrderNo(out_no);
                    itemDetail.setPayStatus(1);
                    //设置支付时间
                    itemDetail.setOrderTime(new Date());
                    //特殊处理 支付时间即为出场时间
                    itemDetail.setOutTime(new Date());
                    //itemDetail(new Date());
                    payOrderMapper.updateById(itemDetail);
                }
            }

            //=========================================================================
            //同步停车场支付账单
            SyncOrderDto syncOrderDto = new SyncOrderDto();
            syncOrderDto.setOrderNo(order.getOrderId());
            syncOrderDto.setAmount(order.getOrderAmt());
            //收费终端 4：微信平台 5：APP（安卓/IOS）
            syncOrderDto.setPayType(4);
            //只支持支付宝和微信 对应关系为1-4 2-5
            //支付金额（amount）的付款方式 1：现金 2: 银行卡 3：电子现金 4: 微信 5：支付宝 6：城市E通卡 7：其它
            syncOrderDto.setPayMethod(order.getPayId() == 1 ? 4 : 5);
            syncOrderDto.setFreeMoney(0);
            syncOrderDto.setFreeTime(0);
            //syncOrderDto.setFreeDetail();
            //调用接口api
            try {
                JSONObject jsonObject = ParkApiUtil.syncOrder(syncOrderDto);
                LOGGER.info("pay order sync success:{}",jsonObject.getString("resCode"));
                if("0".equals(jsonObject.getString("resCode"))) {
                    //订单同步成功之后 将订单同步状态更新
                    order.setSyncStatus(1);
                    orderMapper.updateById(order);
                }else{
                    order.setSyncStatus(0);
                    orderMapper.updateById(order);
                    LOGGER.error("sync order failure:{}", jsonObject.getString("resCode"));
                    throw new BusiException("订单缴费同步失败！");
                }
            }catch(Exception e){
                order.setSyncStatus(0);
                orderMapper.updateById(order);
                LOGGER.error("sync order failure:{}",e.getMessage());
                throw new BusiException("订单缴费同步失败！");
            }
        } else {
            //如果不为1  则为取消支付4
            order.setOrderStatus(Integer.valueOf(status));
            //设置tlinx平台编号
            order.setPOrderId(ord_no);
            order.setPayId(1);
            order.setPayTime(new Date());
            orderMapper.updateById(order);
            if (itemDetail != null && StringUtils.isNotBlank(itemDetail.getOrderNo())) {
                itemDetail.setOrderNo(out_no);
                itemDetail.setPayStatus(Integer.valueOf(status));
                itemDetail.setOrderTime(new Date());
                payOrderMapper.updateById(itemDetail);
            }
        }


    }

}
