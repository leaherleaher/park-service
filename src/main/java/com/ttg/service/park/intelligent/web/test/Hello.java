package com.ttg.service.park.intelligent.web.test;

import com.ttg.service.park.config.properties.ParkProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Title: Hello</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:05
 */
@Controller
@RequestMapping(value="/test")
public class Hello {
    private static final Logger logger = LoggerFactory.getLogger(Hello.class);

    @Autowired
    private ParkProperties parkProperties;
    @GetMapping(value="/hello")
    public String hello(){
        /*logger.debug("debug日志");
        logger.info("info日志");
        logger.warn("warn日志");
        logger.error("errro日志");*/
        /*LogUtil.debug(logger,"debug日志");
        LogUtil.info(logger,"info日志");
        LogUtil.warn(logger,"warn日志");
        LogUtil.error(logger,"errror日志");*/

        LogUtil.info(logger,"info日志");
        LogUtil.error(logger,"error日志");
        LogUtil.warn(logger,"warn日志");
        LogUtil.debug(logger,"debug日志");

//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("user/PaySuccess");
//        mv.addObject("orderNo","234325435");
//
//        return mv;
        String aa = parkProperties.getPaymentInfoUrl();
        System.out.println(aa);
        return aa;
    }
}
