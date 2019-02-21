package com.ttg.service.park.intelligent.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttg.service.park.intelligent.entity.PSysMerchant;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户基础信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PSysMerchantMapper extends BaseMapper<PSysMerchant> {

    PSysMerchant selectByMerId(@Param("merchantId") String merchantId);
}
