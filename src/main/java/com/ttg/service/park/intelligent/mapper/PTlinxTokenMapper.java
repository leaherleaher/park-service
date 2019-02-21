package com.ttg.service.park.intelligent.mapper;

import com.ttg.service.park.intelligent.entity.PTlinxToken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * T-Linx应用令牌信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PTlinxTokenMapper extends BaseMapper<PTlinxToken> {

    PTlinxToken findByAppId(@Param("appId") String appId);
}
