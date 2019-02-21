package com.ttg.service.park.intelligent.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.sys.GetUserPrincipal;
import com.ttg.service.park.common.validator.ValidatorUtils;
import com.ttg.service.park.intelligent.entity.MerchantBasicSetting;
import com.ttg.service.park.intelligent.service.IMerchantBasicSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Api("商户基本配置接口")
@RestController
@RequestMapping("/merchant/setting")
public class MerchantBasicSettingController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantBasicSettingController.class);

    @Autowired
    private IMerchantBasicSettingService iMerchantBasicSettingService;

//    @ApiOperation(value = "当前商户配置信息详情", notes = "当前商户配置信息详情", httpMethod = "GET")
//    @GetMapping(value = "info")
//    public R info() {
//
//        //从权限信息中获取商户mer_id 根据mer_id从数据库中查询商户配置信息
//        String merId = "20777370";  //这里先写死
//
//        try {
//            MerchantBasicSetting merchantBasicSetting = iMerchantBasicSettingService.selectOne(
//                    new EntityWrapper<MerchantBasicSetting>().eq("mer_id", merId));
//            return R.ok().put("data", merchantBasicSetting);
//        } catch (Exception e) {
//            logger.error("Data query failed:{}", e.getMessage());
//            return R.error(-1, "商户信息查询失败");
//        }
//    }

    @ApiOperation(value = "当前商户配置信息修改", notes = "当前商户配置信息修改", httpMethod = "PATCH")
    @PatchMapping(value = "update")
    public R update(@RequestBody MerchantBasicSetting merchantBasicSetting) {
        ValidatorUtils.validateEntity(merchantBasicSetting);

        try {
            iMerchantBasicSettingService.update(merchantBasicSetting);
        } catch (Exception e) {
            logger.error("Data update failed:{}", e.getMessage());
            return R.error(-1, "商户配置更新失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "当前商户配置信息详情", notes = "当前商户配置信息详情", httpMethod = "GET")
    @GetMapping(value = "/info")
    public R infoDetail(@RequestParam Map<String, String> map) {
        String merId = map.get("merId");
        //如果前端传过来的merId不为空  则为用户调用 否则为商户调用
        if (merId == null) {
            //广告模板查询 新增商户过滤 每个商户只能看到自己维护的模板信息
            try {
                merId = GetUserPrincipal.getUserId().getMerchantId();
            } catch (Exception e) {
                logger.error("get merId failure");
                return R.error(-1, "商户id获取失败");
            }
        }
        try {
            MerchantBasicSetting merchantBasicSetting = iMerchantBasicSettingService.selectByMerId(merId);
            return R.ok().put("data", merchantBasicSetting);
        } catch (Exception e) {
            logger.error("DataDetail query failed:{}", e.getMessage());
            return R.error(-1, "商户详情查询失败");

        }
    }
}
