package com.ttg.service.park.intelligent.service.auth.impl;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.i18n.LocalMessageSourceService;
import com.ttg.service.park.common.utils.tlinx.TLinxMD5Encrypt;
import com.ttg.service.park.common.utils.tlinx.TLinxUtil;
import com.ttg.service.park.config.properties.PayProperties;
import com.ttg.service.park.config.properties.TlinxProperties;
import com.ttg.service.park.dto.constant.BusinessConstant;
import com.ttg.service.park.dto.constant.PayConstant;
import com.ttg.service.park.dto.tlinx.*;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PSysBranch;
import com.ttg.service.park.intelligent.entity.PSysMerchant;
import com.ttg.service.park.intelligent.security.UserPrincipal;
import com.ttg.service.park.intelligent.service.IPSysBranchService;
import com.ttg.service.park.intelligent.service.IPSysMerchantService;
import com.ttg.service.park.intelligent.service.IPSysUserService;
import com.ttg.service.park.intelligent.service.IPTlinxAuthService;
import com.ttg.service.park.intelligent.service.auth.IAuthService;
import com.ttg.service.park.intelligent.service.tlinx.ITlinxService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Title: AuthService</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:35
 */
@Service
public class AuthService implements IAuthService {


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private TlinxProperties tlinxProperties;


    @Autowired
    private PayProperties payProperties;

    @Resource
    private IPSysUserService ipSysUserService;

    @Autowired
    private IPSysMerchantService ipSysMerchantService;

    @Autowired
    private IPSysBranchService ipSysBranchService;

    @Autowired
    private IPTlinxAuthService ipTlinxAuthService;

    @Autowired
    private ITlinxService tlinxService;

    @Autowired
    private LocalMessageSourceService messageSourceService;


    @Override
    public UserPrincipal getPrincipal(String userId) {
        //通过CODE去TLINX端获取用户的app_token
        //如果有值，则取值，如果没有值，则需要新建用户的信息，然后进行返回
        UserInfo userInfo = ipTlinxAuthService.getUserInfoById(userId);

        UserPrincipal userPrincipal = null;
        if (Optional.fromNullable(userInfo).isPresent()) {
            userPrincipal = new UserPrincipal();
            userPrincipal.setUserName(userInfo.getUserId());
            userPrincipal.setNickName(userInfo.getUserName());
            userPrincipal.setUserPwd(userInfo.getUserPwd());
            userPrincipal.setActive(userInfo.getUserStatus() == 1);
            userPrincipal.setMerchantId(userInfo.getUserRelation());
            userPrincipal.setMerchantName(userInfo.getMerchantName());
            userPrincipal.setBranchId(userInfo.getBranchId());
            userPrincipal.setBranchName(userInfo.getBranchName());
            userPrincipal.setPaymentTypeId(userInfo.getPaymentTypeId());
            userPrincipal.setPaymentTypeStatus(userInfo.getPaymentTypeStatus());
            userPrincipal.setExpired(false);

            //if (StringUtils.isNotBlank(userInfo.getPaymentTypeId())) {
            userPrincipal.setAuthorities(Lists.newArrayList(new SimpleGrantedAuthority("ROLE_role")));
            //}

        }

        return userPrincipal;
    }

    @Override
    public TlinxAppTokenInfo handleGateway(String trade_type, String token, String data) throws Exception {
        //校验信息是否正常
        if (null == trade_type || !Objects.equal(trade_type, PayConstant.APP_TOKEN)) {
            throw new BusiException(messageSourceService.getMessage(BusinessConstant.ERROR_10000_MSG));
        }
        if (StringUtils.isBlank(token)) {
            throw new BusiException(messageSourceService.getMessage(BusinessConstant.ERROR_10001_MSG));
        }

        if (StringUtils.isBlank(data)) {
            throw new BusiException(messageSourceService.getMessage(BusinessConstant.ERROR_10002_MSG));
        }

        //获取用户信息
        TlinxTokenInfo tokenInfo = tlinxService.getTokenInfo(token);

        TlinxUserInfoResp tlinxUserInfoResp = tlinxService.getTlinxUserInfo(tokenInfo.getToken(), tokenInfo.getAesKey(), "merchant", payProperties.getMerchantDefaultPassword());

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(String.valueOf(tlinxUserInfoResp.getScrId()));
        userInfo.setUserName(tlinxUserInfoResp.getTrueName());
        userInfo.setUserStatus(1);
        userInfo.setUserType(1);
        userInfo.setMerchantId(String.valueOf(tlinxUserInfoResp.getMctNo()));
        userInfo.setMerchantName(tlinxUserInfoResp.getMctName());
        userInfo.setUserRelation(String.valueOf(tlinxUserInfoResp.getMctNo()));
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        passwordEncoder.setEncodeHashAsBase64(true);
        String password = passwordEncoder.encodePassword(payProperties.getMerchantDefaultPassword(), userInfo.getUserId());
        userInfo.setUserPwd(password);
        userInfo.setBranchId(tlinxUserInfoResp.getOrgNo());
        userInfo.setBranchName(tlinxUserInfoResp.getOrgName());
        //校验用户是否存在  不存在则插入
        ipSysUserService.verifyUser(userInfo);

/*        if (StringUtils.isBlank(tlinxUserInfoResp.getShopNo())){
            throw new BusiException("请设置终端门店后再进应用");
        }*/

        /*TlinxMerchant tlinxMerchant = new TlinxMerchant();
        tlinxMerchant.setAppId(tlinxProperties.getAppId());
        tlinxMerchant.setAesKey(tokenInfo.getAesKey());
        tlinxMerchant.setToken(tokenInfo.getToken());
        tlinxMerchant.setMerchantId(String.valueOf(tlinxUserInfoResp.getMctNo()));
        tlinxMerchant.setMerchantName(tlinxUserInfoResp.getMctName());
        tlinxMerchant.setBranchId(tlinxUserInfoResp.getOrgNo());
        tlinxMerchant.setBranchName(tlinxUserInfoResp.getOrgName());
        tlinxMerchant.setCityId(tlinxUserInfoResp.getCityId());
        tlinxMerchant.setCityName(tlinxUserInfoResp.getCity());
        tlinxMerchant.setShopId(tlinxUserInfoResp.getShopNo());
        tlinxMerchant.setLogo(tlinxUserInfoResp.getLogo());*/
        //校验机构与商户信息并保存
        try{
            PSysMerchant merchant = new PSysMerchant();
            merchant.setMerId(String.valueOf(tlinxUserInfoResp.getMctNo()));
            merchant.setMerName(tlinxUserInfoResp.getMctName());
            merchant.setBranchId(tlinxUserInfoResp.getOrgNo());
            merchant.setCityId(tlinxUserInfoResp.getCityId());
            merchant.setShopId(tlinxUserInfoResp.getShopNo());
            merchant.setLogo(tlinxUserInfoResp.getLogo());
            merchant.setStatus(1);
            //校验商户信息并保存
            ipSysMerchantService.verifyMerchant(merchant);

            //校验机构信息并保存
            PSysBranch branch = new PSysBranch();
            branch.setBranchId(tlinxUserInfoResp.getOrgNo());
            branch.setBranchName(tlinxUserInfoResp.getOrgName());
            ipSysBranchService.verifyBranch(branch);
        }catch(Exception e){
            LOGGER.error("商户和机构验证失败！");

        }

        //根据TOKEN获取应用的访问令牌
        String apptoken = TLinxMD5Encrypt.md5(
                token
                        + tlinxProperties.getAppId()
                        + tlinxUserInfoResp.getScrId()
                        + String.valueOf(System
                        .currentTimeMillis())
                        + RandomStringUtils.randomAlphanumeric(5),
                "UTF-8").toLowerCase(); // 应用访问令牌
        String returnData = TLinxUtil.AESEncrypt(
                "{\"app_token\":\"" + apptoken + "\"}",
                tokenInfo.getAesKey()); // AES加密
        TlinxAppTokenInfo tlinxAppTokenInfo = new TlinxAppTokenInfo();
        tlinxAppTokenInfo.setAesKey(tokenInfo.getAesKey());
        tlinxAppTokenInfo.setAppId(tlinxProperties.getAppId());
        tlinxAppTokenInfo.setAppToken(apptoken);
        tlinxAppTokenInfo.setData(returnData);

        TlinxAuthInfo tlinxAuthInfo = new TlinxAuthInfo();

        tlinxAuthInfo.setAppId(tlinxProperties.getAppId());
        tlinxAuthInfo.setAesKey(tokenInfo.getAesKey());
        tlinxAuthInfo.setAppToken(apptoken);
        tlinxAuthInfo.setExchangeToken(token);
        tlinxAuthInfo.setUserId(tlinxUserInfoResp.getScrId());
        tlinxAuthInfo.setToken(tokenInfo.getToken());

        //校验应用令牌信息并保存
        ipTlinxAuthService.verifyAppToken(tlinxAuthInfo);

        return tlinxAppTokenInfo;
    }

    @Override
    public UserInfo getUserInfo(String appId, String app_token) {
        UserInfo userInfo = ipTlinxAuthService.getUserInfo(tlinxProperties.getAppId(),app_token);
                //merchantAuthClient.getUserInfoByAppToken(tlinxProperties.getAppId(), app_token);
        return userInfo;
    }

}
