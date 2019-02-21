package com.ttg.service.park.intelligent.service.auth.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.utils.tlinx.TLinxMD5Encrypt;
import com.ttg.service.park.common.utils.tlinx.TLinxUtil;
import com.ttg.service.park.config.properties.TlinxProperties;
import com.ttg.service.park.dto.constant.PayConstant;
import com.ttg.service.park.dto.pay.*;
import com.ttg.service.park.dto.tlinx.TlinxAppTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxAuthInfo;
import com.ttg.service.park.dto.tlinx.TlinxTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxUserInfoResp;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PSysUser;
import com.ttg.service.park.intelligent.entity.PTlinxAuth;
import com.ttg.service.park.intelligent.entity.PTlinxSecurity;
import com.ttg.service.park.intelligent.entity.PTlinxToken;
import com.ttg.service.park.intelligent.mapper.PSysUserMapper;
import com.ttg.service.park.intelligent.mapper.PTlinxSecurityMapper;
import com.ttg.service.park.intelligent.service.IPTlinxAuthService;
import com.ttg.service.park.intelligent.service.IPTlinxSecurityService;
import com.ttg.service.park.intelligent.service.IPTlinxTokenService;
import com.ttg.service.park.intelligent.service.auth.ITTlinxService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.jcache.JCacheCache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * Package cn.ttg.pay.center.admin.service.tlinx
 * Class TlinxService
 * Description
 * Created by Ardy Zhang
 * Date 2017/7/4
 * Time 11:13
 */
@Service
@Transactional
public class TTlinxService implements ITTlinxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TTlinxService.class);


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TlinxProperties tlinxProperties;

    @Autowired
    private IPTlinxTokenService ipTlinxTokenService;

    @Autowired
    private PTlinxSecurityMapper tlinxSecurityMapper;

    @Autowired
    private IPTlinxSecurityService ipTlinxSecurityService;

    @Autowired
    private PSysUserMapper userMapper;

    @Autowired
    private IPTlinxAuthService ipTlinxAuthService;

    //@Autowired
    //private CacheManager cacheManager;

    @Override
    public TlinxTokenInfo getTokenInfo(String token) {

        /** 获取访问令牌 */
        Map paramMap = new HashMap();
        paramMap.put("app_id", tlinxProperties.getAppId());
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("dev_id", tlinxProperties.getDevId());
        paramMap.put("token", token);
        String appTokenUrl = tlinxProperties.getApiDomainUrl() + tlinxProperties.getApiTokenUrl() + "?app_id="
                + tlinxProperties.getAppId() + "&dev_id=" + tlinxProperties.getDevId() + "&token=" + token
                + "&sign=" + TLinxUtil.sign(paramMap);
        String sb = restTemplate.getForEntity(appTokenUrl, String.class).getBody();

        if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            throw new BusiException("TLINX返回数据验签失败");
        }
        JSONObject jsonObject = JSON.parseObject(sb);
        String jsonData = StringUtils.isBlank(String.valueOf(jsonObject.get("data"))) ? null : String.valueOf(jsonObject.get("data"));

        if (StringUtils.isBlank(jsonData)) {
            throw new BusiException(jsonObject.get("msg").toString());
        }
        String decryptData = null; // AES解密
        try {
            decryptData = TLinxUtil.AESDecrypt(
                    jsonData, tlinxProperties.getAppSecret());
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error(Throwables.getStackTraceAsString(e));

        }
        LOGGER.info("xxxxxx:{}", decryptData);
        TlinxTokenInfo tlxTokenParam = JSON.toJavaObject((JSON) JSONObject.parse(decryptData), TlinxTokenInfo.class);
        LOGGER.info("sssss:{}", tlxTokenParam);

        return tlxTokenParam;
    }


    @Override
    @CachePut(value = "token",key = "'payToken'")
    @Scheduled(fixedDelay= 7000*1000,initialDelay = 7000*1000)
    public TlinxTokenInfo getAppTokenInfoFromTlinx() {
        TlinxTokenInfo tlxTokenParam =null;
        //令牌失效或没有，去TLINX去取令牌信息
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime() / 1000);

        /** 获取访问令牌 */
        Map paramMap = new HashMap();
        paramMap.put("app_id", tlinxProperties.getAppId());
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("dev_id", tlinxProperties.getDevId());
        paramMap.put("timestamp", timestamp);
        String appTokenUrl = tlinxProperties.getApiDomainUrl() + tlinxProperties.getAppTokenUrl() + "?app_id=" + tlinxProperties.getAppId() + "&dev_id=" + tlinxProperties.getDevId()
                + "&timestamp=" + timestamp + "&sign=" + TLinxUtil.sign(paramMap);
        String sb = restTemplate.getForEntity(appTokenUrl, String.class).getBody();

        if (TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            JSONObject jsonObject = JSON.parseObject(sb);
            Integer errcode = StringUtils.isBlank(jsonObject.get("errcode").toString()) ? -1 : (Integer) jsonObject.get("errcode");
            if (errcode != 0) {
                throw new BusiException(jsonObject.get("msg").toString());
            }
            String jsonData = StringUtils.isBlank(jsonObject.getString("data")) ? null : String.valueOf(jsonObject.get("data"));

            if (null != jsonData) {
                String decryptData = null; // AES解密
                try {
                    decryptData = TLinxUtil.AESDecrypt(
                            jsonData, tlinxProperties.getAppSecret());
                } catch (Exception e) {
                    //e.printStackTrace();
                    LOGGER.error(Throwables.getStackTraceAsString(e));

                }
                LOGGER.info("xxxxxx:{}", decryptData);
                tlxTokenParam = JSON.toJavaObject((JSON) JSONObject.parse(decryptData), TlinxTokenInfo.class);
                LOGGER.info("sssss:{}", tlxTokenParam);
                tlxTokenParam.setAppId(tlinxProperties.getAppId());
                tlxTokenParam.setExpiredDate(org.apache.commons.lang.time.DateUtils.addSeconds(date, tlxTokenParam.getExpiredTime()));
                return tlxTokenParam;
            } else {
                throw new BusiException(jsonObject.get("msg").toString());
            }
        } else {
            throw new BusiException("TLINX返回数据验签失败");
        }
    }


    @Override
    public TlinxTokenInfo getAppTokenInfo() {
        TlinxTokenInfo tlxTokenParam = new TlinxTokenInfo();
        //先到数据库中查找令牌信息，是看是否过期
        PTlinxToken tlinxToken = ipTlinxTokenService.findByAppId(tlinxProperties.getAppId());
        Optional optional = Optional.fromNullable(tlinxToken);
        boolean flag = false;
        if (optional.isPresent()) {
            // 如果还在生效期
            if (tlinxToken.getExpiredDate().getTime() > new Date().getTime() + 100) {
                tlxTokenParam.setToken(tlinxToken.getToken());
                tlxTokenParam.setAesKey(tlinxToken.getAesKey());
            } else {
                flag = true;
            }
        } else {
            flag = true;
            tlinxToken = new PTlinxToken();
        }


        if (flag) {
            //令牌失效或没有，去TLINX去取令牌信息
            Date date = new Date();
            String timestamp = String.valueOf(date.getTime() / 1000);
            String clear = "true";


            /** 获取访问令牌 */
            Map paramMap = new HashMap();
            paramMap.put("app_id", tlinxProperties.getAppId());
            paramMap.put("app_secret", tlinxProperties.getAppSecret());
            paramMap.put("dev_id", tlinxProperties.getDevId());
            paramMap.put("timestamp", timestamp);
            String appTokenUrl = tlinxProperties.getApiDomainUrl() + tlinxProperties.getAppTokenUrl() + "?app_id=" + tlinxProperties.getAppId() + "&dev_id=" + tlinxProperties.getDevId()
                    + "&timestamp=" + timestamp + "&sign=" + TLinxUtil.sign(paramMap);
            String sb = restTemplate.getForEntity(appTokenUrl, String.class).getBody();

            if (TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
                JSONObject jsonObject = JSON.parseObject(sb);
                Integer errcode = StringUtils.isBlank(jsonObject.get("errcode").toString()) ? -1 : (Integer) jsonObject.get("errcode");
                if (errcode != 0) {
                    throw new BusiException(jsonObject.get("msg").toString());
                }
                String jsonData = StringUtils.isBlank(jsonObject.getString("data")) ? null : String.valueOf(jsonObject.get("data"));

                if (null != jsonData) {
                    String decryptData = null; // AES解密
                    try {
                        decryptData = TLinxUtil.AESDecrypt(
                                jsonData, tlinxProperties.getAppSecret());
                    } catch (Exception e) {
                        //e.printStackTrace();
                        LOGGER.error(Throwables.getStackTraceAsString(e));

                    }
                    LOGGER.info("xxxxxx:{}", decryptData);
                    tlxTokenParam = JSON.toJavaObject((JSON) JSONObject.parse(decryptData), TlinxTokenInfo.class);
                    LOGGER.info("sssss:{}", tlxTokenParam);
                    if (optional.isPresent()) {
                        tlinxToken.setExpiredDate(DateUtils.addSeconds(date, tlxTokenParam.getExpiredTime()));
                        tlinxToken.setExpiredTime(tlxTokenParam.getExpiredTime());
                        tlinxToken.setToken(tlxTokenParam.getToken());
                        tlinxToken.setAesKey(tlxTokenParam.getAesKey());
                        ipTlinxTokenService.updateById(tlinxToken);
                    } else {

                        tlinxToken.setAppId(tlinxProperties.getAppId());
                        tlinxToken.setExpiredDate(DateUtils.addSeconds(date, tlxTokenParam.getExpiredTime()));
                        tlinxToken.setExpiredTime(tlxTokenParam.getExpiredTime());
                        tlinxToken.setToken(tlxTokenParam.getToken());
                        tlinxToken.setAesKey(tlxTokenParam.getAesKey());
                        ipTlinxTokenService.insert(tlinxToken);
                    }
                } else {
                    throw new BusiException(jsonObject.get("msg").toString());
                }
            } else {
                throw new BusiException("TLINX返回数据验签失败");
            }
        }
        return tlxTokenParam;
    }

    @Override
    public UserInfo getTlinxUserInfo(String newtoken, String aeskey, String formBy, String defaultPwd) throws Exception {

        /** 获取用户数据 */
        Map paramMap = new HashMap();
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("token", newtoken);
        String tlinx_user_url = tlinxProperties.getApiDomainUrl() + tlinxProperties.getUserUrl() + "?token="
                + newtoken + "&sign=" + TLinxUtil.sign(paramMap);
        String sb = restTemplate.getForEntity(tlinx_user_url, String.class).getBody();

        if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            throw new BusiException("TLINX响应信息验签失败");
        }
        JSONObject obj2 = JSONObject.parseObject(sb);

        String tokenData = StringUtils.isBlank(obj2.getString("data")) ? null
                : obj2.get("data").toString(); // 数据
        if (StringUtils.isBlank(tokenData)) {
            throw new BusiException("TLINX响应信息中DATA域数据不存在");
        }
        String decryptData = TLinxUtil.AESDecrypt(tokenData,
                aeskey); // AES解密

        if (StringUtils.isBlank(decryptData)) {
            throw new BusiException("解析TLINX响应信息DATA数据域失败");
        }
        TlinxUserInfoResp userInfoResp = JSON.parseObject(decryptData, TlinxUserInfoResp.class);


        LOGGER.info("获取的TLINX用户基础信息:{}", userInfoResp);

        //将用户基础信息进行入库操作
        /** 保存Tlinx用户信息 */
        UserInfo userInfo = this.addTlinxUser(userInfoResp, formBy, defaultPwd);


        // 获取Tlinx门店数据
        this.getTlinxShopData(newtoken, aeskey, userInfoResp.getMctNo(),
                userInfoResp.getMctName(), userInfoResp.getBrandName(), userInfoResp.getLogo());


        return userInfo;
    }

    @Override
    public TlinxAppTokenInfo getAppTokenInfo(String oldToken, String formBy, String defaultPwd) throws Exception {
        TlinxTokenInfo tokenInfo = this.getTokenInfo(oldToken);
        UserInfo userInfo = this.getTlinxUserInfo(tokenInfo.getToken(), tokenInfo.getAesKey(), formBy, defaultPwd);
        //生成令牌信息
        return this.changeAppToken(tokenInfo.getToken(), oldToken, tlinxProperties.getAppId(), tokenInfo.getAesKey(), userInfo);
    }

    @Override
    public void verifyAppToken(TlinxAuthInfo tlinxAuthInfo) {
        PTlinxAuth tlinxAuth = ipTlinxAuthService.findByAppToken(tlinxAuthInfo.getAppId(), tlinxAuthInfo.getAppToken());

        if (Optional.fromNullable(tlinxAuth).isPresent()) {

            tlinxAuth.setAppId(tlinxAuthInfo.getAppId()); // 应用ID
            tlinxAuth.setAppToken(tlinxAuthInfo.getAppToken()); // 用户标识令牌
            tlinxAuth.setExchangeToken(tlinxAuthInfo.getExchangeToken()); // 交换令牌
            tlinxAuth.setToken(tlinxAuthInfo.getToken()); // 令牌
            tlinxAuth.setAesKey(tlinxAuthInfo.getAesKey()); // AES加密密钥
            tlinxAuth.setUserId(tlinxAuthInfo.getUserId()); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            ipTlinxAuthService.updateById(tlinxAuth);

        } else {
            tlinxAuth = new PTlinxAuth();
            tlinxAuth.setAppId(tlinxAuthInfo.getAppId()); // 应用ID
            tlinxAuth.setAppToken(tlinxAuthInfo.getAppToken()); // 用户标识令牌
            tlinxAuth.setExchangeToken(tlinxAuthInfo.getExchangeToken()); // 交换令牌
            tlinxAuth.setToken(tlinxAuthInfo.getToken()); // 令牌
            tlinxAuth.setAesKey(tlinxAuthInfo.getAesKey()); // AES加密密钥
            tlinxAuth.setUserId(tlinxAuthInfo.getUserId()); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            ipTlinxAuthService.insert(tlinxAuth);
        }
    }

    @Override
    public void getBranchInfo(String appId, String aesKey, String token) throws Exception {
        /** 获取用户数据 */
        Map paramMap = new HashMap();
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("token", token);
        String tlinx_user_url = tlinxProperties.getApiDomainUrl() + tlinxProperties.getViewMerchantUrl() + "?token="
                + token + "&sign=" + TLinxUtil.sign(paramMap);
        String sb = restTemplate.postForEntity(tlinx_user_url, null, String.class).getBody();

        if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            throw new BusiException("TLINX响应信息验签失败");
        }
        JSONObject obj2 = JSONObject.parseObject(sb);

        String tokenData = StringUtils.isBlank(obj2.getString("data").toString()) ? null
                : obj2.get("data").toString(); // 数据
        if (StringUtils.isBlank(tokenData)) {
            throw new BusiException("TLINX响应信息中DATA域数据不存在");
        }
        String decryptData = TLinxUtil.AESDecrypt(tokenData,
                aesKey); // AES解密

        if (StringUtils.isBlank(decryptData)) {
            throw new BusiException("解析TLINX响应信息DATA数据域失败");
        }

        LOGGER.info("decryptData:{}", decryptData);

    }


    private TlinxAppTokenInfo changeAppToken(String newtoken, String oldToken, String appId, String aeskey, UserInfo userInfo) throws Exception {


        String apptoken = TLinxMD5Encrypt.md5(
                newtoken
                        + appId
                        + userInfo.getUserId()
                        + String.valueOf(System
                        .currentTimeMillis())
                        + RandomStringUtils.randomAlphanumeric(5),
                "UTF-8").toLowerCase(); // 应用访问令牌
        String returnData = TLinxUtil.AESEncrypt(
                "{\"app_token\":\"" + apptoken + "\"}",
                aeskey); // AES加密
        // TODO: 2017/3/24
        LOGGER.info("++++++>:{}", aeskey);
        /** 查询Tlinx应用授权信息 */
        PTlinxAuth tlinxAuth = ipTlinxAuthService.findByAppToken(appId, apptoken);
        if (Optional.fromNullable(tlinxAuth).isPresent()) {

            tlinxAuth.setAppId(appId); // 应用ID
            tlinxAuth.setAppToken(apptoken); // 用户标识令牌
            tlinxAuth.setExchangeToken(oldToken); // 交换令牌
            tlinxAuth.setToken(newtoken); // 令牌
            tlinxAuth.setAesKey(aeskey); // AES加密密钥
            tlinxAuth.setUserId(Integer.valueOf(userInfo.getUserId())); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            ipTlinxAuthService.updateById(tlinxAuth);

        } else {

            tlinxAuth.setAppId(appId); // 应用ID
            tlinxAuth.setToken(apptoken); // 用户标识令牌
            tlinxAuth.setExchangeToken(oldToken); // 交换令牌
            tlinxAuth.setToken(newtoken); // 令牌
            tlinxAuth.setAesKey(aeskey); // AES加密密钥
            tlinxAuth.setUserId(Integer.valueOf(userInfo.getUserId())); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            ipTlinxAuthService.insert(tlinxAuth);
        }
        TlinxAppTokenInfo tlinxAppTokenInfo = new TlinxAppTokenInfo();
        tlinxAppTokenInfo.setAesKey(aeskey);
        tlinxAppTokenInfo.setAppId(appId);
        tlinxAppTokenInfo.setAppToken(apptoken);

        return tlinxAppTokenInfo;
    }

    private UserInfo addTlinxUser(TlinxUserInfoResp userInfoResp, String formBy, String defaultPwd) {
        //先去查看是否存在用户，如果存在，则不用处理，如果不存在，则更新
        PSysUser user = userMapper.selectByUserId(String.valueOf(userInfoResp.getScrId()));
        if (Optional.fromNullable(user).isPresent()) {

        } else {
            user = new PSysUser();
            user.setUserId(String.valueOf(userInfoResp.getScrId()));
            user.setUserName(userInfoResp.getTrueName());
            user.setBranchId(String.valueOf(userInfoResp.getMctNo()));
            user.setUserStatus(1);
            user.setUserRelation(userInfoResp.getShopNo());
            user.setUserType(Objects.equal(formBy, PayConstant.MERCHANT_FORM) ? 1 : 0);
            user.setPassword(defaultPwd);
            userMapper.insert(user);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setUserName(user.getUserName());
        userInfo.setUserStatus(user.getUserStatus());
        userInfo.setUserType(user.getUserType());
        userInfo.setMerchantId(user.getBranchId());
        userInfo.setMerchantName(userInfoResp.getMctName());
        return userInfo;
    }

    private void getTlinxShopData(String newtoken, String aeskey,
                                  Integer mct_no, String mct_name, String brand_name, String logo)
            throws Exception {
        String page = "1", pagesize = "100";
        String data = TLinxUtil.AESEncrypt("{\"page\":\"" + page
                + "\",\"pagesize\":\"" + pagesize + "\"}", aeskey); // AES加密

        /** 获取门店数据 */
        Map paramMap = new HashMap();
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("token", newtoken);
        paramMap.put("data", data);
        String tlinx_shop_url = tlinxProperties.getApiDomainUrl() + tlinxProperties.getShopUrl() + "?token="
                + newtoken + "&sign=" + TLinxUtil.sign(paramMap);
        String sb = restTemplate.postForEntity(tlinx_shop_url, "data=" + data, String.class).getBody();

        if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            throw new BusiException("TLINX响应信息验签失败");
        }
        JSONObject obj = JSONObject.parseObject(sb);
        Integer errcode = StringUtils.isBlank(obj.get("errcode").toString()) ? -1 : (Integer) obj.get("errcode");
        if (errcode != 0) {
            throw new BusiException(obj.get("msg").toString());
        }
        String tokenData = StringUtils.isBlank(obj.getString("data").toString()) ? null
                : obj.get("data").toString(); // 数据
        if (StringUtils.isBlank(tokenData)) {
            throw new BusiException("TLINX响应信息DATA域数据异常");
        }
        String decryptData = TLinxUtil.AESDecrypt(tokenData,
                aeskey); // AES解密
        if (StringUtils.isBlank(decryptData)) {
            throw new BusiException("解析TLINX响应信息DATA数据域失败");
        }

        JSONObject obj3 = JSONObject.parseObject(decryptData);
        LOGGER.info("obj3:{}", obj3);

    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false,rollbackFor = Exception.class)
    public PTlinxSecurity getTlinxSecurity(String shopId, TlinxTokenInfo tlxTokenParam) {

        LOGGER.info("获取的token信息：{}", tlxTokenParam);
        PTlinxSecurity shopSecurity = ipTlinxSecurityService.findByShopId(Integer.valueOf(shopId));
                tlinxSecurityMapper.findByShopId(Integer.valueOf(shopId));
        Optional optional = Optional.fromNullable(shopSecurity);
        boolean flag = false;
        if (optional.isPresent()) {
            if (shopSecurity.getStatus() == 1) {
                return shopSecurity;
            } else {
                flag = true;
            }
        } else {
            shopSecurity = new PTlinxSecurity();
            flag = true;
        }

        //如果没有或失效，则去TLINX去取
        try {
            if (flag) {
                TreeMap<String, String> map = new TreeMap();
                map.put("shop_no", shopId);
                //map.put("app_id",tlinxProperties.getAppId());
                map.put("token", tlxTokenParam.getToken());

                String data = TLinxUtil.AESEncrypt(JSON.toJSONString(map), tlxTokenParam.getAesKey());
                LOGGER.info("data:{}", JSON.toJSONString(map));
                TreeMap<String, String> param = new TreeMap<String, String>();
                param.put("data", data);
                param.put("token", tlxTokenParam.getToken());
                param.put("app_secret", tlinxProperties.getAppSecret());

                String url = tlinxProperties.getApiDomainUrl() + tlinxProperties.getShopUrl() + "/open_key?token="
                        + tlxTokenParam.getToken() + "&sign=" + TLinxUtil.sign(param);
                String dataStr = "data=" + data;


                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpEntity<String> formEntity = new HttpEntity<String>(dataStr, headers);
                LOGGER.info("url:{}", url);

                String sb = restTemplate.postForObject(url, formEntity, String.class);

                //验证签名
                LOGGER.info("sb:{}", sb);
                if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
                    throw new BusiException("TLINX返回数据验签失败");
                }
                JSONObject obj2 = JSONObject.parseObject(sb);

                Integer error = (Integer) obj2.get("errcode"); // 数据
                if (error != 0) {
                    throw new BusiException(obj2.get("msg").toString());
                }
                String tokenData2 = StringUtils.isBlank(String.valueOf(obj2.get("data"))) ? null
                        : obj2.get("data").toString(); // 数据
                if (null == tokenData2) {
                    throw new BusiException("TLINX响应数据DATA域数据为空");
                }
                String decryptData2 = TLinxUtil.AESDecrypt(tokenData2,
                        tlxTokenParam.getAesKey()); // AES解密
                //decryptData2 = decryptData2.replaceAll("[\\[\\]]","");
                JSONObject obj3 = JSONObject.parseObject(decryptData2);
                LOGGER.info("obj3:{}", obj3);
                if (optional.isPresent()) {
                    shopSecurity.setOpenId(String.valueOf(obj3.get("open_id")));
                    shopSecurity.setOpenKey(String.valueOf(obj3.get("open_key")));
                    shopSecurity.setStatus(Integer.parseInt(obj3.get("status").toString()));
                    shopSecurity.setModifyTime(new Date());
                    ipTlinxSecurityService.updateById(shopSecurity);
                } else {
                    shopSecurity.setShopId(Integer.valueOf(shopId));
                    shopSecurity.setOpenId(String.valueOf(obj3.get("open_id")));
                    shopSecurity.setOpenKey(String.valueOf(obj3.get("open_key")));
                    shopSecurity.setStatus(Integer.parseInt(obj3.get("status").toString()));
                    ipTlinxSecurityService.insert(shopSecurity);
                }

            }
        } catch (Exception e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            shopSecurity.setStatus(0);
            shopSecurity.setShopId(Integer.valueOf(shopId));
        }
        return shopSecurity;
    }

    @Override
    public TlinxPreOrderRespParam placeOrder(TlinxPreOrderReqParam preOrderParam, TlinxReqParam tlinxReqParam) {

        try {
            LOGGER.info("业务数据:{}", JSON.toJSONString(preOrderParam));
            //先对业务数据进行加密
            String data = TLinxUtil.AESEncrypt(JSON.toJSONString(preOrderParam), tlinxReqParam.getOpenKey());

            preOrderParam.setOpenId(tlinxReqParam.getOpenId());
            preOrderParam.setTimestamp(String.valueOf(new Date().getTime() / 1000));
            preOrderParam.setData(data);
            //系统数据

            preOrderParam.setOpenKey(tlinxReqParam.getOpenKey());
            LOGGER.info("Parameters:{}", preOrderParam.getAllParameters());
            preOrderParam.setSign(TLinxUtil.sign1(preOrderParam.getAllParameters()));
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.setAll(preOrderParam.getAllParameters());
            // TODO: 2017/7/4
            String url = tlinxProperties.getApiBusiUrl() + tlinxProperties.getApiBusiPayOrderUrl();
            String sb = restTemplate.postForEntity(url, multiValueMap, String.class).getBody();

            //验证签名
            LOGGER.info("sb:{}", sb);
            JSONObject obj2 = JSONObject.parseObject(sb);
            if(Integer.parseInt(String.valueOf(obj2.get("errcode")))>0){
                LOGGER.info("请求URL:{}",url);
                LOGGER.info(obj2.get("msg") + ":{}", sb);
                throw new BusiException((String) obj2.get("msg"));
            }
            // TODO: 2017/3/26
            if (!TLinxUtil.signVerfity1(sb, tlinxReqParam.getOpenKey())) {
                LOGGER.info("请求URL:{}",url);
                LOGGER.info("TLINX返回数据验签失败:{}", sb);
                throw new BusiException("TLINX返回数据验签失败");
            }

            String tokenData2 = StringUtils.isBlank(String.valueOf(obj2.get("data"))) ? null
                    : obj2.get("data").toString(); // 数据
            if (null == tokenData2) {
                LOGGER.info("请求URL:{}",url);
                throw new BusiException("返回数据为空");
            }
            String decryptData2 = TLinxUtil.AESDecrypt(tokenData2,
                    tlinxReqParam.getOpenKey()); // AES解密
            LOGGER.debug("decryptData2:{}", decryptData2);
            //decryptData2 = decryptData2.replaceAll("[\\[\\]]","");
            //此时从tlinx返回的支付状态为2
            //TlinxPreOrderRespParam(pmtName=支付宝, pmtTag=AlipayPABZ, ordMctId=628, ordShopId=69,
            // ordNo=9154640944385998441745216, ordType=null, originalAmount=null, discountAmount=null, ignoreAmount=null,
            // tradeAccount=null, tradeNo=null, tradeAmount=1, tradeQrcode=null, tradePayTime=null, status=2, tradeResult=null,
            // outNo=000120190102141039489CRM001, jsapiPayUrl=https://q.tlinx.com?f=9154640944385998441745216&O=cf0fe96ef424ba4a1f559b48b6499e06)
            TlinxPreOrderRespParam obj3 = JSON.parseObject(decryptData2, TlinxPreOrderRespParam.class);
            LOGGER.debug("obj3:{}", obj3);
            return obj3;


        } catch (Exception e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            throw new BusiException("获取支付URL失败", e);
        }
    }

    @Override
    public ShopPayType isSupportWxRecharge(TlinxReqParam param,String p) throws Exception {
        ShopPayType shopPayType = new ShopPayType();

        //如果STATUS不为1，说明不能调用支付接口，无法充值
        if (!"1".equals(param.getStatus())) {
            shopPayType.setFlag(false);
            return shopPayType;
        }

        TlinxShopPayTypeReqParam shopPayTypeReqParam = new TlinxShopPayTypeReqParam();
        shopPayTypeReqParam.setOpenId(param.getOpenId());
        shopPayTypeReqParam.setTimestamp(String.valueOf(new Date().getTime() / 1000));

        String pmtType = "0,1,2,3";
        shopPayTypeReqParam.setPmtType(pmtType);
        //data字段加密并bin2hex
        String data = TLinxUtil.AESEncrypt(JSON.toJSONString(shopPayTypeReqParam), param.getOpenKey());

        shopPayTypeReqParam.setOpenKey(param.getOpenKey());
        shopPayTypeReqParam.setData(data);

        shopPayTypeReqParam.setSign(TLinxUtil.sign1(shopPayTypeReqParam.getAllParameters()));
        LOGGER.debug("shopPayTypeReqParam.getAllParameters():{}", shopPayTypeReqParam.getAllParameters());
        String url = tlinxProperties.getApiBusiUrl() + tlinxProperties.getApiBusiPayListUrl();
        LOGGER.debug("url:{}", url);

        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.setAll(shopPayTypeReqParam.getAllParameters());

        String sb = restTemplate.postForEntity(url, multiValueMap, String.class).getBody();

        List<TlinxShopPayTypeRespParam> obj3 = null;
        //验证签名
        LOGGER.debug("sb:{}", sb);
        if (!TLinxUtil.signVerfity1(sb, param.getOpenKey())) {
            LOGGER.info("请求URL:{}",url);
            LOGGER.error("TLINX响应数据验签失败:{}", sb);
            throw new BusiException("TLINX响应数据验签失败");
        }
        JSONObject obj2 = JSONObject.parseObject(sb);
        if (null != obj2) {
            String tokenData2 = StringUtils.isBlank(String.valueOf(obj2.get("data"))) ? null
                    : obj2.get("data").toString(); // 数据
            if (null != tokenData2) {
                String decryptData2 = TLinxUtil.AESDecrypt(tokenData2,
                        param.getOpenKey()); // AES解密
                LOGGER.info("decryptData2:{}", decryptData2);
                //decryptData2 = decryptData2.replaceAll("[\\[\\]]","");
                obj3 = JSON.parseArray(decryptData2, TlinxShopPayTypeRespParam.class);
                LOGGER.info("obj3:{}", obj3);
            }
        }


        if (obj3 != null && obj3.size() > 0) {
            for (TlinxShopPayTypeRespParam re : obj3) {
                if (re.getPmtTag().startsWith(p)) {
                    shopPayType.setFlag(true);
                    shopPayType.setPayType(re.getPmtTag());
                    break;
                }
            }
        }
        return shopPayType;
    }



    @Override
    public TlinxPayStatusRespParam queryPayStatus(String openId, String orderId, String openKey) {

        try {
            TlinxPayStatusReqParam payStatusReqParam = new TlinxPayStatusReqParam();
            payStatusReqParam.setOutNo(orderId);
            payStatusReqParam.setOpenId(openId);
            payStatusReqParam.setOpenKey(openKey);
            LOGGER.info("业务数据:{}", JSON.toJSONString(payStatusReqParam));
            //先对业务数据进行加密
            String data = TLinxUtil.AESEncrypt(JSON.toJSONString(payStatusReqParam), payStatusReqParam.getOpenKey());

            payStatusReqParam.setOpenId(payStatusReqParam.getOpenId());
            payStatusReqParam.setTimestamp(String.valueOf(new Date().getTime() / 1000));
            payStatusReqParam.setData(data);
            //系统数据

            payStatusReqParam.setOpenKey(payStatusReqParam.getOpenKey());
            LOGGER.info("sdfsfsdf:{}", payStatusReqParam.getAllParameters());
            payStatusReqParam.setSign(TLinxUtil.sign1(payStatusReqParam.getAllParameters()));
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.setAll(payStatusReqParam.getAllParameters());
            // TODO: 2017/7/4
            String url = tlinxProperties.getApiBusiUrl() + tlinxProperties.getApiBusiPayStatusUrl();
            String sb = restTemplate.postForEntity(url, multiValueMap, String.class).getBody();

            //验证签名
            LOGGER.info("sb:{}", sb);
            // TODO: 2017/3/26
            if (!TLinxUtil.signVerfity1(sb, payStatusReqParam.getOpenKey())) {
                throw new BusiException("TLINX返回数据验签失败");
            }

            JSONObject obj2 = JSONObject.parseObject(sb);

            String tokenData2 = StringUtils.isBlank(String.valueOf(obj2.get("data"))) ? null
                    : obj2.get("data").toString(); // 数据
            if (null == tokenData2) {
                throw new BusiException("返回数据为空");
            }
            String decryptData2 = TLinxUtil.AESDecrypt(tokenData2,
                    payStatusReqParam.getOpenKey()); // AES解密
            LOGGER.info("decryptData2:{}", decryptData2);
            //decryptData2 = decryptData2.replaceAll("[\\[\\]]","");
            TlinxPayStatusRespParam tlinxPayStatusRespParam = JSON.parseObject(decryptData2,TlinxPayStatusRespParam.class);
            LOGGER.info("obj3:{}", tlinxPayStatusRespParam);
            return tlinxPayStatusRespParam;


        } catch (Exception e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            throw new BusiException("查询支付结果失败");
        }
    }
}
