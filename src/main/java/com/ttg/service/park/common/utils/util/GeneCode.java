package com.ttg.service.park.common.utils.util;


/**
 * <p>Title: GeneCode</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-14 15:23
 */
public class GeneCode {

    //生成规则 前缀+时间戳+五位随机码
    public static String getCode(String prefix){
        return prefix+System.currentTimeMillis()+(int)(Math.random()*100000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++){
            System.out.println(getCode("temp"));
        }
    }
}
