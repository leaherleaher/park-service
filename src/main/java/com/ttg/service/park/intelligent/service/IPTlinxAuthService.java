package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.dto.tlinx.TlinxAuthInfo;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PTlinxAuth;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * T-Linx应用授权信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPTlinxAuthService extends IService<PTlinxAuth> {
    //校验令牌信息并保存
    void verifyAppToken(TlinxAuthInfo tlinxAuthInfo);

    //TLINX商户云平台授权用户信息接口
    UserInfo getUserInfo(String appId,String apptoken);

    UserInfo getUserInfoById(String userId);

    PTlinxAuth findByAppToken(String appId, String appToken);
}
