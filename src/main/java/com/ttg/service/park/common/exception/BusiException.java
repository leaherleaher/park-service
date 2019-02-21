package com.ttg.service.park.common.exception;

/**
 * Package cn.ttg.pay.center.exception
 * Class BusiException
 * Description
 * Created by Ardy Zhang
 * Date 2017/6/23
 * Time 10:19
 */
public class BusiException extends RuntimeException {

    public BusiException() {
    }

    public BusiException(String message) {
        super(message);
    }

    public BusiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusiException(Throwable cause) {
        super(cause);
    }

    public BusiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
