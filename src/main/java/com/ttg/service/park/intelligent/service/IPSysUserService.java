package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.dto.vo.BaseVo;
import com.ttg.service.park.intelligent.entity.PSysUser;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPSysUserService extends IService<PSysUser> {

    //验证用户是否存在
    void verifyUser(UserInfo userInfo);
}
