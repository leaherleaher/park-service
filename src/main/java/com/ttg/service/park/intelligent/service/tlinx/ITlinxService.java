package com.ttg.service.park.intelligent.service.tlinx;


import com.ttg.service.park.dto.tlinx.TlinxTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxUserInfoResp;

/**
 * Package cn.ttg.pay.center.admin.service.tlinx
 * Class ITTlinxService
 * Description
 * Created by Ardy Zhang
 * Date 2017/7/4
 * Time 11:13
 */
public interface ITlinxService {
    TlinxTokenInfo getTokenInfo(String token);

    TlinxUserInfoResp getTlinxUserInfo(String token, String aesKey, String formBy, String defaultPwd) throws Exception;


    TlinxUserInfoResp getAppTokenInfo(String oldToken, String formBy, String defaultPwd) throws Exception;
}
