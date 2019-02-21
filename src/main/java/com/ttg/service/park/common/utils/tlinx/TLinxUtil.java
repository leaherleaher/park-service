/**
 * @Filename: TLinxUtil.java
 * @Author：caiqf
 * @Date：2016-4-12
 */
package com.ttg.service.park.common.utils.tlinx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Class: TLinxUtil.java
 * @Description: TLinx工具类
 * @Author：caiqf
 * @Date：2016-4-12
 */
@SuppressWarnings("all")
public class TLinxUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TLinxUtil.class);
    // 排序
    public static String sort(Map paramMap){
        String sort = "";
        TLinxMapUtil signMap = new TLinxMapUtil();
        if (paramMap != null) {
            String key;
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                String value = ((paramMap.get(key) != null) && (!(""
                        .equals(paramMap.get(key).toString())))) ? paramMap
                        .get(key).toString() : "";
                signMap.put(key, value);
            }
            signMap.sort();
            for (Iterator it = signMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                sort = sort + key + "=" + signMap.get(key).toString() + "&";
            }
            if ((sort != null) && (!("".equals(sort)))) {
                sort = sort.substring(0, sort.length() - 1);
            }
        }
        System.out.println("sort = " + sort + "");
        return sort;
    }

    // MD5签名（app_secret）
    public static String sign(Map paramMap)  {
        String sign = TLinxMD5Encrypt.md5(TLinxUtil.sort(paramMap), "UTF-8")
                .toLowerCase();
        return sign;
    }

    // MD5签名验证（app_secret）
    public static boolean signVerfity(String jsonData, String app_secret)
             {
        boolean flag = false;
        if ((jsonData != null) && (!("".equals(jsonData)))) {
            String verfitysign = "";
            Map paramMap = new HashMap();
            paramMap.put("app_secret", app_secret);
            JSONObject jsonObj = JSONObject.parseObject(jsonData);
            LinkedHashMap<String, String> json = JSON.parseObject(jsonData, new TypeReference<LinkedHashMap<String, String>>() {
            });

            for (Map.Entry<String, String> map : json.entrySet()) {
                if (Objects.equal("sign", map.getKey())) {
                    verfitysign = map.getValue();
                    break;
                }
                paramMap.put(map.getKey(), map.getValue());

            }
            String sign = sign(paramMap);
            LOGGER.info("sign:{}",sign);
            LOGGER.info("verfitysign:{}",verfitysign);
            if ((verfitysign != null) && (sign.equals(verfitysign)))
                flag = true;
        }
        return flag;
    }

    // SHA1+MD5签名（open_key）
    public static String sign1(Map paramMap){
        String sign = TLinxMD5Encrypt.md5(
                TLinxSHA1.SHA1(TLinxUtil.sort(paramMap), "utf-8"), "UTF-8")
                .toLowerCase();
        return sign;
    }

    // SHA1+MD5签名验证（open_key）
    public static boolean signVerfity1(String jsonData, String open_key)
            throws Exception {
        boolean flag = false;
        if ((jsonData != null) && (!("".equals(jsonData)))) {
            String verfitysign = "";
            Map paramMap = new HashMap();
            paramMap.put("open_key", open_key);
            LinkedHashMap<String, String> json = JSON.parseObject(jsonData, new TypeReference<LinkedHashMap<String, String>>() {
            });
            for (Map.Entry<String, String> map : json.entrySet()) {
                if (Objects.equal("sign", map.getKey())) {
                    verfitysign = map.getValue();
                    break;
                }
                paramMap.put(map.getKey(), map.getValue());
            }

            String sign = sign1(paramMap);
            System.out.println("sign = [" + sign + "]");
            if ((verfitysign != null) && (sign.equals(verfitysign)))
                flag = true;
        }
        return flag;
    }

    // AES加密
    public static String AESEncrypt(String jsonData, String aeskey)
            throws Exception {
        return TLinxAESCoder.encrypt(jsonData, aeskey);
    }

    // AES解密
    public static String AESDecrypt(String data, String aeskey)
            throws Exception {
        return TLinxAESCoder.decrypt(data, aeskey);
    }
}
