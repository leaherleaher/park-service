package com.ttg.service.park.intelligent.service.auth;


import com.ttg.service.park.dto.pay.*;
import com.ttg.service.park.dto.tlinx.TlinxAppTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxAuthInfo;
import com.ttg.service.park.dto.tlinx.TlinxTokenInfo;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PTlinxSecurity;

/**
 * Package cn.ttg.pay.center.admin.service.tlinx
 * Class ITTlinxService
 * Description
 * Created by Ardy Zhang
 * Date 2017/7/4
 * Time 11:13
 */
public interface ITTlinxService {
    TlinxTokenInfo getTokenInfo(String token);


    PTlinxSecurity getTlinxSecurity(String shopId, TlinxTokenInfo tlxTokenParam);

    TlinxPreOrderRespParam placeOrder(TlinxPreOrderReqParam preOrderParam, TlinxReqParam tlinxReqParam);

    ShopPayType isSupportWxRecharge(TlinxReqParam param,String p) throws Exception;

    //TlinxTokenInfo getAppTokenInfo1();

    TlinxTokenInfo getAppTokenInfoFromTlinx();

    TlinxTokenInfo getAppTokenInfo();

    UserInfo getTlinxUserInfo(String token, String aesKey, String formBy, String defaultPwd) throws Exception;

    TlinxAppTokenInfo getAppTokenInfo(String oldToken,String formBy,String defaultPwd) throws Exception;

    void verifyAppToken(TlinxAuthInfo tlinxAuthInfo);

    void getBranchInfo(String appId, String aesKey, String token) throws Exception;

    TlinxPayStatusRespParam queryPayStatus(String openId, String orderId, String openKey);
}
