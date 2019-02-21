package com.ttg.service.park.intelligent.mapper;

import com.ttg.service.park.intelligent.entity.PCOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 缴费订单表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
public interface PCOrderMapper extends BaseMapper<PCOrder> {

    PCOrder selectByOrderId(@Param("orderId") String orderNo);
}
