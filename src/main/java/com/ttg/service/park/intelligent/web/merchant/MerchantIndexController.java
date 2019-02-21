package com.ttg.service.park.intelligent.web.merchant;

import com.ttg.service.park.intelligent.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: MerchantIndexController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 14:39
 */
@RequestMapping("/merchant")
@Controller
public class MerchantIndexController {

    @RequestMapping("/index")
    public String index(Model model){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Integer paymentTypeStatus =userPrincipal.getPaymentTypeStatus();

        //model.addAttribute("paymentTypeStatus", paymentTypeStatus);
        return "admin/index";
    }


}
