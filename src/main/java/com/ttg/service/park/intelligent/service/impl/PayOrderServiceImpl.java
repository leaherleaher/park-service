package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttg.service.park.common.sys.GetUserPrincipal;
import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.common.utils.util.Query;
import com.ttg.service.park.dto.vo.PaySuccessVo;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.ttg.service.park.intelligent.mapper.PayOrderMapper;
import com.ttg.service.park.intelligent.security.UserPrincipal;
import com.ttg.service.park.intelligent.service.IPayOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-10
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    private static Logger logger = LoggerFactory.getLogger(PayOrderServiceImpl.class);
    
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        //车牌号
        String plateNo = (String) params.get("plateNo");
        //支付起始时间
        String payTimeStart = (String) params.get("payTimeStart");
        //支付结束时间
        String payTimeEnd = (String) params.get("payTimeEnd");

        //加入商户过滤  只能查询本商户信息
        String merId = null;
        try {
            merId = GetUserPrincipal.getUserId().getMerchantId();
        }catch(Exception e){
            logger.error("get merId failure");
            return null;
        }

        Page<PayOrder> page = this.selectPage(
                new Query<PayOrder>(params).getPage(),
                new EntityWrapper<PayOrder>()
                .like(StringUtils.isNotBlank(plateNo), "plate_no", plateNo)
                //.between("pay_time",payTimeStart,payTimeEnd)
                .ge(StringUtils.isNotBlank(payTimeStart),"DATE_FORMAT(pay_time,'%Y-%m-%d')",payTimeStart)
                .le(StringUtils.isNotBlank(payTimeStart),"DATE_FORMAT(pay_time,'%Y-%m-%d')",payTimeEnd)
                .eq("pay_status",1)
                .orderBy("order_time",false)
                .eq(StringUtils.isNotBlank(merId),"mer_id",merId)
        );
        return new PageUtils(page);
    }
    
    /**
     * @Description //TODO 停车订单保存
     * @Param [payOrder]
     * @return java.lang.Integer
     **/
    @Override
    public Integer save(PayOrder payOrder) {
       return payOrderMapper.insert(payOrder);
    }

    @Override
    public PageUtils queryPlateNo(Map<String, Object> params) {
        //车牌号
        String plateNo = (String) params.get("plateNo");

        Page<PayOrder> page = this.selectPageList(
                new Query<PayOrder>(params).getPage(),
                new EntityWrapper<PayOrder>()
                        .like(StringUtils.isNotBlank(plateNo), "plate_no", plateNo)
        );
        return new PageUtils(page);
    }

    /**
     * @Description //TODO 根据车牌号查询出所有的停车缴费记录
     * @Param [params]
     * @return com.ttg.service.park.common.utils.util.PageUtils
     **/
    @Override
    public PageUtils queryPlateNoInfo(Map<String, Object> params) {
        //车牌号
        String plateNo = (String) params.get("plateNo");

        Page<PayOrder> page = this.selectPage(
                new Query<PayOrder>(params).getPage(),
                new EntityWrapper<PayOrder>()
                        .eq(StringUtils.isNotBlank(plateNo), "plate_no", plateNo)
                        .eq("pay_status",1)
        );
        return new PageUtils(page);
    }

    @Override
    public PaySuccessVo findSeccessInfo(String orderNo) {
        return payOrderMapper.findSuccessInfo(orderNo);
    }

    public Page<PayOrder> selectPageList(Page<PayOrder> page, Wrapper<PayOrder> wrapper) {
        wrapper = (Wrapper<PayOrder>) SqlHelper.fillWrapper(page, wrapper);
        //page.setRecords(this.baseMapper.selectPage(page, wrapper));
        page.setRecords(payOrderMapper.selectPlateNoList(page,wrapper));
        return page;
    }


}
