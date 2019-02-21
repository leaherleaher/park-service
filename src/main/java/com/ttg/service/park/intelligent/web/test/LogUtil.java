package com.ttg.service.park.intelligent.web.test;

import org.slf4j.Logger;

/**
 * <p>Title: LogUtil</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-19 13:29
 */
public class LogUtil {

    public static void info(Logger logger, String content){
        String contentNew = appendLine(content);
        logger.info(contentNew);
    }

    public static void debug(Logger logger, String content){
        String contentNew = appendLine(content);
        logger.debug(contentNew);
    }

    public static void warn(Logger logger, String content){
        String contentNew = appendLine(content);
        logger.warn(contentNew);
    }

    public static void error(Logger logger, String content){
        String contentNew = appendLine(content);
        logger.error(contentNew);
    }

    public static String appendLine(String content){
        Throwable throwable = new Throwable();
        //LogUtils.LOGI的depth是0, 该函数的depth是1, 调用者的depth是2,调用者的调用者是3
        int methodDepth = 2;
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        System.out.println(stackTraceElements.length);
        if (methodDepth < stackTraceElements.length) {
            StackTraceElement element = stackTraceElements[methodDepth];
            if (element != null && element.getFileName() != null) {
                String className = element.getFileName().substring(0, element.getFileName().lastIndexOf("."));
                return content = String.format("[%d]%s", element.getLineNumber(), content);

            }
        }
        return null;
    }

}
