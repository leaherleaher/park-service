package com.ttg.service.park.intelligent.service.impl;

import com.ttg.service.park.intelligent.entity.PCOrder;
import com.ttg.service.park.intelligent.mapper.PCOrderMapper;
import com.ttg.service.park.intelligent.service.IPCOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 缴费订单表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Service
public class PCOrderServiceImpl extends ServiceImpl<PCOrderMapper, PCOrder> implements IPCOrderService {

    @Autowired
    private PCOrderMapper pcOrderMapper;
    @Override
    public PCOrder selectByOrderId(String orderNo) {
        return pcOrderMapper.selectByOrderId(orderNo);
    }
}
