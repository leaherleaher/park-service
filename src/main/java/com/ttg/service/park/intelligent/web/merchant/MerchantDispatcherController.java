package com.ttg.service.park.intelligent.web.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: MerchantDispatcherController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-28 20:25
 */
@Controller
@RequestMapping(value="/merchant")
public class MerchantDispatcherController {
    //停车统计
    @RequestMapping(value="/park/computed")
    public String computed(){
        return "admin/ParkComputed";
    }

    //车牌统计
    @RequestMapping(value="/park/manage")
    public String manage(){
        return "admin/ParkManage";
    }

    //车牌详情
    @RequestMapping(value="/park/info")
    public String info(){
        return "admin/ParkNONGUESTS";
    }

    //营销管理 --模板详情
    @RequestMapping(value="/market/manage")
    public String maketmanage(){
        return "admin/Manage";
    }

    //广告类型详情
    @RequestMapping(value="/market/admanage")
    public String maketadmanage(){
        return "admin/Manage2";
    }

    //模板维护
    @RequestMapping(value="/market/edit")
    public String maketedit(){
        return "admin/ManageChild";
    }

    //广告类型详情
    @RequestMapping(value="/market/adedit")
    public String maketadedit(){
        return "admin/ManageChild2";
    }

    //基本配置
    @RequestMapping(value="/basic/manage")
    public String basicmanage(){
        return "admin/Basic";
    }
    @RequestMapping(value="/basic/edit")
    public String basicedit(){
        return "admin/BasicChild";
    }


}
