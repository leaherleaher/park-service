package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.intelligent.entity.PCOrder;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 缴费订单表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
public interface IPCOrderService extends IService<PCOrder> {

    PCOrder selectByOrderId(String orderNo);

}
