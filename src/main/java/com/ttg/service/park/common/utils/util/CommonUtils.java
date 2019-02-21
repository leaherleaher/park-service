package com.ttg.service.park.common.utils.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * <p>Title: CommonUtils</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 17:39
 */
public class CommonUtils {
    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().
                replaceAll("-","").substring(0,32);

        return uuid;
    }


    /**
     * md5常用工具类
     * @param data
     * @return
     */
    public static String MD5(String data){
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            //return sb.toString().toUpperCase();
            return sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String str = CommonUtils.MD5("aaa");
        System.out.println(str);
    }
}
