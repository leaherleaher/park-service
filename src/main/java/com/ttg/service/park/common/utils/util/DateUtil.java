package com.ttg.service.park.common.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>Title: DateUtil</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 17:49
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();

    /**
     * @return java.util.Date
     * @Description //TODO 字符串转换为
     * @Param [str]
     * 注意 备注： yyyyMMdd中 MM必须大写 否则无法得到正确月份
     **/
    public static Date parse(String str) {
        SimpleDateFormat sdf = local.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            local.set(sdf);
        }
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            logger.error("日期格式转换错误！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return java.util.Date
     * @Description //TODO日期转换
     * @Param [str]
     **/
    public static String format(Date date) {
        SimpleDateFormat sdf = local.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyyMMdd",Locale.US);
            local.set(sdf);
        }
        return sdf.format(date);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(format(new Date()));
    }


}
