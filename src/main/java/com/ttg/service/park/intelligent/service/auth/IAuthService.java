package com.ttg.service.park.intelligent.service.auth;

import com.ttg.service.park.dto.tlinx.TlinxAppTokenInfo;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.security.UserPrincipal;

/**
 * <p>Title: IAuthService</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:32
 */
public interface IAuthService {

    UserPrincipal getPrincipal(String userId);

    TlinxAppTokenInfo handleGateway(String trade_type, String token, String data) throws Exception;

    UserInfo getUserInfo(String appId, String app_token);

}
