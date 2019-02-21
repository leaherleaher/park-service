package com.ttg.service.park.intelligent.service.impl;

import com.google.common.base.Optional;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PSysUser;
import com.ttg.service.park.intelligent.mapper.PSysUserMapper;
import com.ttg.service.park.intelligent.service.IPSysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PSysUserServiceImpl extends ServiceImpl<PSysUserMapper, PSysUser> implements IPSysUserService {

    @Autowired
    private PSysUserMapper pSysUserMapper;

    @Override
    public void verifyUser(UserInfo userInfo) {
        //查询用户
        PSysUser user = pSysUserMapper.selectByUserId(userInfo.getUserId());

        if (!Optional.fromNullable(user).isPresent()) {
            user = new PSysUser();
            user.setUserId(userInfo.getUserId());
            user.setUserName(userInfo.getUserName());
            user.setBranchId(userInfo.getBranchId());
            user.setUserStatus(userInfo.getUserStatus());
            user.setUserRelation(userInfo.getUserRelation());
            user.setUserType(userInfo.getUserType());
            user.setPassword(userInfo.getUserPwd());
            //插入用户信息
            pSysUserMapper.insert(user);
        } else {
            user.setBranchId(userInfo.getBranchId());
            pSysUserMapper.updateById(user);
        }
    }
}
