package com.ttg.service.park.intelligent.mapper;

import com.ttg.service.park.intelligent.entity.PSysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PSysUserMapper extends BaseMapper<PSysUser> {

    PSysUser selectByUserId(@Param("userId") String userId);
}
