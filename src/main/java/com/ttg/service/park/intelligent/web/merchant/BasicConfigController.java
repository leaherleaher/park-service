package com.ttg.service.park.intelligent.web.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: BasicConfigController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 16:03
 */
@Controller
@RequestMapping(value="/merchant/basic")
public class BasicConfigController {
    @GetMapping(value="/list")
    public String basicList(){
        return "merchant/basic_list";
    }

}
