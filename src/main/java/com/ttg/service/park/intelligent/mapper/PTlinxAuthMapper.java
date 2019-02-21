package com.ttg.service.park.intelligent.mapper;

import com.ttg.service.park.intelligent.entity.PTlinxAuth;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * T-Linx应用授权信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PTlinxAuthMapper extends BaseMapper<PTlinxAuth> {

    PTlinxAuth selectByAppToken(@Param("appId") String appId, @Param("apptoken") String apptoken);
}
