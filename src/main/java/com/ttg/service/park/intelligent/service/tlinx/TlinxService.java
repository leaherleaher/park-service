package com.ttg.service.park.intelligent.service.tlinx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.utils.tlinx.TLinxUtil;
import com.ttg.service.park.config.properties.TlinxProperties;
import com.ttg.service.park.dto.tlinx.TlinxTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxUserInfoResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * Package cn.ttg.pay.center.admin.service.tlinx
 * Class TlinxService
 * Description
 * Created by Ardy Zhang
 * Date 2017/7/4
 * Time 11:13
 */
@Service
public class TlinxService implements ITlinxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlinxService.class);


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TlinxProperties tlinxProperties;



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
    public TlinxUserInfoResp getTlinxUserInfo(String newtoken, String aeskey, String formBy, String defaultPwd) throws Exception {

        /** 获取用户数据 */
        Map paramMap = new HashMap();
        paramMap.put("app_secret", tlinxProperties.getAppSecret());
        paramMap.put("token", newtoken);
        //获取tlinxUrl
        String tlinx_user_url = tlinxProperties.getApiDomainUrl() + tlinxProperties.getUserUrl() + "?token="
                + newtoken + "&sign=" + TLinxUtil.sign(paramMap);

        String sb = restTemplate.getForEntity(tlinx_user_url, String.class).getBody();

        //md5验证签名
        if (!TLinxUtil.signVerfity(sb, tlinxProperties.getAppSecret())) {
            throw new BusiException("TLINX响应信息验签失败");
        }
        JSONObject obj2 = JSONObject.parseObject(sb);

        String tokenData = StringUtils.isBlank(obj2.get("data").toString()) ? null
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

        // 获取Tlinx门店数据
        //this.getTlinxShopData(newtoken, aeskey, userInfoResp.getMctNo(),userInfoResp.getMctName(), userInfoResp.getBrandName(), userInfoResp.getLogo());


        return userInfoResp;
    }

    @Override
    public TlinxUserInfoResp getAppTokenInfo(String oldToken, String formBy, String defaultPwd) throws Exception {
        return null;
    }

}
