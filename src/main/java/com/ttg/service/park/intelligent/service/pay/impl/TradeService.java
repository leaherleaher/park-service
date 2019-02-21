package com.ttg.service.park.intelligent.service.pay.impl;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.dto.pay.*;
import com.ttg.service.park.dto.tlinx.TlinxTokenInfo;
import com.ttg.service.park.intelligent.entity.PCOrder;
import com.ttg.service.park.intelligent.entity.PSysMerchant;
import com.ttg.service.park.intelligent.entity.PTlinxSecurity;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.ttg.service.park.intelligent.mapper.PCOrderMapper;
import com.ttg.service.park.intelligent.mapper.PSysMerchantMapper;
import com.ttg.service.park.intelligent.mapper.PayOrderMapper;
import com.ttg.service.park.intelligent.service.IPCOrderService;
import com.ttg.service.park.intelligent.service.pay.ITradeService;
import com.ttg.service.park.intelligent.service.auth.ITTlinxService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Title: TradeService</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 15:53
 */
@Service
@Transactional
public class TradeService implements ITradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private PCOrderMapper orderMapper;

    @Autowired
    private IPCOrderService ipcOrderService;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PSysMerchantMapper pSysMerchantMapper;

    @Autowired
    private ITTlinxService tlinxService;


    @Override
    public TradeInfo getUserTradeInfo(String merchantId, String paymentCode, String scode) {
        return null;
    }

    @Override
    @Transactional
    public PlaceOrderInfo handleUserPay(TradeUserPayReq tradeUserPayReq) {
        //先判断商户信息
        PSysMerchant merchant = pSysMerchantMapper.selectByMerId(tradeUserPayReq.getMerId());

        LOGGER.info("tradeUserPayReq:{}",tradeUserPayReq);
        if (!Optional.fromNullable(merchant).isPresent()) {
            LOGGER.debug("商户信息不存在");
            throw new BusiException("商户信息不存在");
        }


        //对缴费金额进行校验
        if (tradeUserPayReq.getPayable().compareTo(tradeUserPayReq.getPayable()) != 0) {
            throw new BusiException("用户的缴费金额不正确");
        }

        if (StringUtils.isNotBlank(tradeUserPayReq.getOrderNo())) {
            //查看订单记录信息 判断订单是否存在
            PCOrder order = ipcOrderService.selectByOrderId(tradeUserPayReq.getOrderNo());
            if (Optional.fromNullable(order).isPresent() ){
                //如果订单状态为1  则该订单已经完成支付
                if(order.getOrderStatus() == 1) {
                    throw new BusiException("此缴费单已经缴费完成，请勿重复操作,缴费代码：[" + tradeUserPayReq.getPayable() + "]，订单号：[" + order.getPOrderId() + "]");
                }else if (order.getOrderStatus()==0){
                    //如果该订单状态为0，支付期间不允许重新支付，默认为15分钟才能支付
                    //todo 判断支付定单时间
                    throw new BusiException("此缴费单中有正在缴费的内容，请15分钟后再试。");
                }
            }
        }

        //生成新的缴费订单信息  调用tlinx的支付接口  生成对应引擎的支付链接
        //String orderId = Hashing.md5().newHasher(16).putString(tradeUserPayReq.getMerchantId(), Charsets.UTF_8).putString(tradeUserPayReq.getScode(), Charsets.UTF_8).putLong(System.nanoTime()).hash().toString();
        //调用停车场接口生成的账单号 不用自己生成订单编号
        String orderId = tradeUserPayReq.getOrderNo();
        PCOrder order = new PCOrder();
        order.setOrderId(orderId);
        order.setOrderAmt(tradeUserPayReq.getPayable());
        order.setMerId(tradeUserPayReq.getMerId());
        //order.setPaymentCode(tradeUserPayReq.getPaymentCode());
        //此处唯一身份识别标识 改为车票号
        order.setScode(tradeUserPayReq.getPlateNo());
        order.setSyncStatus(0);
        order.setOrderTime(new Date());
        //order.setPayId(1);  //设置支付方式为微信支付
        //根据浏览器引擎判断支付方式
        //payId为1：微信支付  为2：支付宝支付
        if(tradeUserPayReq.getPayTypeCode().equals("Weixin")){
            order.setPayId(1);
        }else{
            order.setPayId(2);
        }
        //不需要设置是收款还是支付 停车缴费都是支付
        //order.setPayType(0);
        //向TLINX提交订单
        // TODO: 2017/7/4
        orderMapper.insert(order);
        LOGGER.info("插入的订单信息为：order:{}",order);
        TlinxReqParam reqParam = this.getRechargeReqParam(merchant.getShopId());
        //判断商户是否开通了对应的支付功能
        ShopPayType shopPayType = null;
        try {
            shopPayType = tlinxService.isSupportWxRecharge(reqParam,tradeUserPayReq.getPayTypeCode());
        } catch (Exception e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            throw new BusiException("商户门店支持的支付类型未能获取到");
        }
        if (!shopPayType.isFlag()){
            throw new BusiException("商户支付接口为非支付状态，不能调用商户支付接口");
        }
        LOGGER.info("获取门店对应的支付类型shopPayType:{}",shopPayType);
        String placeOrderUrl = this.createPreRechargeUrl(
                orderId,
                shopPayType.getPayType(),
                tradeUserPayReq.getPlateNo(),
                order.getOrderAmt(),
                tradeUserPayReq.getNotifyUrl(),
                tradeUserPayReq.getCallbackUri(),
                reqParam);
        LOGGER.info("获取支付链接:{}",placeOrderUrl);
        if (StringUtils.isBlank(placeOrderUrl)){
            throw new BusiException("支付链接未获取到或获取失败");
        }
        //进行下单操作
        //orderMapper.insert(order);

        //订单保存之后保存缴费明细  即查询的停车缴费明细单
        PayOrder payOrder = new PayOrder();
        payOrder.setMerId(tradeUserPayReq.getMerId());
        payOrder.setPlateNo(tradeUserPayReq.getPlateNo());
        payOrder.setParkId(tradeUserPayReq.getParkId());
        payOrder.setOrderNo(tradeUserPayReq.getOrderNo());
        payOrder.setParkName(tradeUserPayReq.getParkName());
        payOrder.setEntryTime(tradeUserPayReq.getEntryTime());
        payOrder.setElapsedTime(tradeUserPayReq.getElapsedTime());
        payOrder.setImg(tradeUserPayReq.getImgName());
        payOrder.setPayable(tradeUserPayReq.getPayable());
        payOrder.setDelayTime(tradeUserPayReq.getDelayTime());
        //设置查询时间
        payOrder.setPayTime(tradeUserPayReq.getPayTime());
        //设置订单支付时间时间
        //payOrder.setOrderTime(new Date());
        //设置改缴费明细的支付状态为未支付
        payOrder.setPayStatus(2);
        //订单生成之后插入缴费明细表
        payOrderMapper.insert(payOrder);


        PlaceOrderInfo placeOrderInfo = new PlaceOrderInfo();
        placeOrderInfo.setMerchantId(tradeUserPayReq.getMerId());
        placeOrderInfo.setUserCode(tradeUserPayReq.getPlateNo());
        placeOrderInfo.setOrderId(orderId);
        placeOrderInfo.setPayAmt(tradeUserPayReq.getPayable());

        placeOrderInfo.setPayUrl(placeOrderUrl);
        return placeOrderInfo;

    }


    private String createPreRechargeUrl(String orderId, String payType, String orderName, Integer payAmt, String notifyUrl, String jumpUrl, TlinxReqParam reqParam) {
        //支付操作
        //TlinxReqParam rechargeReqParam = this.getRechargeReqParam(shopId);
        TlinxPreOrderReqParam preOrderParam = new TlinxPreOrderReqParam();

        //业务数据
        preOrderParam.setOutNo(orderId);
        preOrderParam.setPmtTag(payType);
        preOrderParam.setOriginalAmount(payAmt);
        preOrderParam.setRemark("stop pay");
        preOrderParam.setTradeAmount(payAmt);
        preOrderParam.setJumpUrl(jumpUrl);
        preOrderParam.setOrdName(orderName);
        preOrderParam.setNotifyUrl(notifyUrl);

        TlinxPreOrderRespParam tlinxPreOrderRespParam = this.placeOrder(preOrderParam, reqParam);
        LOGGER.debug("下单结果:{}",tlinxPreOrderRespParam);
        if (tlinxPreOrderRespParam==null){
            return null;
        }
        PCOrder order = ipcOrderService.selectByOrderId(orderId);
        order.setPOrderId(tlinxPreOrderRespParam.getOrdNo());
        order.setOrderStatus(tlinxPreOrderRespParam.getStatus());
        orderMapper.updateById(order);
        return tlinxPreOrderRespParam.getJsapiPayUrl();

    }

    private TlinxReqParam getRechargeReqParam(String tshopId) {
        TlinxTokenInfo tlxTokenParam = tlinxService.getAppTokenInfo();
        TlinxReqParam rechargeReqParam = new TlinxReqParam();

        PTlinxSecurity tlinxShopSecurity = tlinxService.getTlinxSecurity(tshopId, tlxTokenParam);
        rechargeReqParam.setOpenId(tlinxShopSecurity.getOpenId());
        rechargeReqParam.setOpenKey(tlinxShopSecurity.getOpenKey());
        rechargeReqParam.setToken(tlxTokenParam.getToken());
        rechargeReqParam.setStatus(String.valueOf(tlinxShopSecurity.getStatus()));

        return rechargeReqParam;
    }


    private TlinxPreOrderRespParam placeOrder(TlinxPreOrderReqParam preOrderParam, TlinxReqParam rechargeReqParam) {
        return tlinxService.placeOrder(preOrderParam, rechargeReqParam);
    }
}
