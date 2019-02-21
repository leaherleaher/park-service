package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.dto.vo.PaySuccessVo;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-10
 */
public interface IPayOrderService extends IService<PayOrder> {

    //停车统计分页查询
    PageUtils queryPage(Map<String, Object> params);

    //停车缴费订单保存
    Integer save(PayOrder payOrder);

    //查询所有停车场车牌
    PageUtils queryPlateNo(Map<String, Object> params);
    //根据车牌号查询所有停车信息
    PageUtils queryPlateNoInfo(Map<String, Object> params);

    PaySuccessVo findSeccessInfo(String orderNo);
}
