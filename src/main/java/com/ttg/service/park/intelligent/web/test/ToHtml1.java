package com.ttg.service.park.intelligent.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: ToHtml1</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 10:06
 */
@Controller
@RequestMapping(value="html")
public class ToHtml1 {

    @RequestMapping(value="/login")
    public String request(){
        return "/auth/login";
    }
}
