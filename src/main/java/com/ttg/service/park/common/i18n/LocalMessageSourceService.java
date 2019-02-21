package com.ttg.service.park.common.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Package cn.ttg.pay.center.core.i18n
 * Class LocalMessageSourceService
 * Description 国际化
 * Created by Ardy Zhang
 * Date 2017/6/13
 * Time 14:36
 */
@Component
public class LocalMessageSourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalMessageSourceService.class);


    @Resource
    private MessageSource messageSource;


    public String getMessage(String code) {

        return this.getMessage(code, new Object[]{});

    }


    public String getMessage(String code, String defaultMessage, Locale locale) {

        return this.getMessage(code, null, defaultMessage, locale);

    }

    public String getMessage(String code, Object[] args) {

        return this.getMessage(code, args, "");

    }

    public String getMessage(String code, String defaultMessage) {

        return this.getMessage(code, null, defaultMessage);

    }


    public String getMessage(String code, Object[] args, Locale locale) {

        return this.getMessage(code, args, "", locale);

    }

    public String getMessage(String code, Object[] args, String defaultMessage) {

        //这里使用比较方便的方法，不依赖request.

        Locale locale = LocaleContextHolder.getLocale();

        return this.getMessage(code, args, defaultMessage, locale);

    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {

        return messageSource.getMessage(code, args, defaultMessage, locale);

    }
}
