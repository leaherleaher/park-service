package com.ttg.service.park.intelligent.web.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Title: UserDispatcherController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-28 20:35
 */
@Controller
@RequestMapping(value="/user")
public class UserDispatcherController {

    //停车费扫码查询
    @RequestMapping(value="/pay/search")
    public String seach(){
        return "user/SearchPage";
    }

    //停车费支付
    @PostMapping(value="/pay/order")
    public ModelAndView order(String data, String plateId, String merId){
        ModelAndView mv = new ModelAndView();
        mv.addObject("data",data);
        mv.addObject("plateId",plateId);
        mv.addObject("merId",merId);

        mv.setViewName("user/ParkMessage");
        return mv;
        //return "user/ParkMessage";
    }

    //停车费扫码查询
    @RequestMapping(value="/pay/success")
    public String success(){
        return "user/PaySuccess";
    }

    //停车费扫码查询
    @RequestMapping(value="/pay/failure")
    public String fail(){
        return "user/PayFail";
    }
}
