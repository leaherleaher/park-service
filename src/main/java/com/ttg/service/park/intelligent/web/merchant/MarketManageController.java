package com.ttg.service.park.intelligent.web.merchant;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: MarketManageController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 15:59
 */
@Controller
@RequestMapping(value="/merchant/market")
public class MarketManageController {
    @GetMapping(value="/list")
    public String list(){
        return "merchant/market_list";
    }
}
