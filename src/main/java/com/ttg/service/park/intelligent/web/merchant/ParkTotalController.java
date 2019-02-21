package com.ttg.service.park.intelligent.web.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: ParkTotalController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 15:55
 */
@Controller
@RequestMapping(value="/merchant/park")
public class ParkTotalController {

    @GetMapping(value="/list")
    public String parkList(){

        return "merchant/park_list";
    }
}
