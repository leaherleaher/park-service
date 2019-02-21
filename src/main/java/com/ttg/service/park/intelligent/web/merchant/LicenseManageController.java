package com.ttg.service.park.intelligent.web.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: LicenseManageController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 15:57
 */
@Controller
@RequestMapping(value="/merchant/license")
public class LicenseManageController {

    @GetMapping(value="/list")
    public String licenseList(){
        return "merchant/license_list";
    }
}
