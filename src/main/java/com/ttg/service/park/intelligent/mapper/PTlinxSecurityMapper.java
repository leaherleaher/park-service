package com.ttg.service.park.intelligent.mapper;

import com.ttg.service.park.intelligent.entity.PTlinxSecurity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * T-Linx商户令牌信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PTlinxSecurityMapper extends BaseMapper<PTlinxSecurity> {

    PTlinxSecurity findByShopId(@Param("shopId") int shopId);
}
