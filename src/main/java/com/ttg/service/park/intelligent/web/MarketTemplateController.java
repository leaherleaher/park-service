package com.ttg.service.park.intelligent.web;


import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.validator.ValidatorUtils;
import com.ttg.service.park.dto.constant.BusinessConstant;
import com.ttg.service.park.intelligent.entity.MarketTemplate;
import com.ttg.service.park.intelligent.entity.TemplateCategory;
import com.ttg.service.park.intelligent.service.IMarketTemplateService;
import com.ttg.service.park.intelligent.service.ITemplateCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
@Api("营销管理接口")
@RestController
@RequestMapping("/merchant/Advertisement")
public class MarketTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateCategoryController.class);

    @Autowired
    private IMarketTemplateService iMarketTemplateService;

    @ApiOperation(value="广告模板清单",notes = "广告模板清单",httpMethod = "GET")
    @GetMapping(value="/list")
    public R getList(){
        List<MarketTemplate> list = null;
        try {
            list = iMarketTemplateService.getList();
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }

        return R.ok().put("data",list);
    }

    @ApiOperation(value="查询展示模板清单",notes = "查询展示模板清单",httpMethod = "GET")
    @GetMapping(value="enList/{merId}")
    public R getEnList(@PathVariable String merId){
        //获取参数
        ///String status = (String)map.get("status");
        try{
            List<MarketTemplate> list = iMarketTemplateService.getEnList(merId);
            return R.ok().put("data",list.get(0));
        }catch(Exception e){
            logger.error("Data query faild:{}",e.getMessage());
            return R.error(-1, BusinessConstant.ERROR);
        }

    }


    @ApiOperation(value="广告模板详情",notes = "广告模板详情",httpMethod = "GET")
    @GetMapping(value="/info/{id}")
    public R info(@PathVariable String id){
        try {
            MarketTemplate marketTemplate = iMarketTemplateService.selectById(id);
            return R.ok().put("data",marketTemplate);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

    @ApiOperation(value="广告模板新增",notes = "广告模板新增",httpMethod = "POST")
    @PostMapping(value="/add")
    public R save(@RequestBody MarketTemplate marketTemplate){
        //判断有效模板状态是否>1=
        if(iMarketTemplateService.getCount(marketTemplate.getTempCategoryCode())>=1&&marketTemplate.getTemplateStatus()==1){
            return R.error(-2,"每种广告只能维护一种有效类型");
        }
        ValidatorUtils.validateEntity(marketTemplate);
        try {
            iMarketTemplateService.save(marketTemplate);
        }catch(Exception e){
            logger.error("Data insert failed:{}",e.getMessage());
            return R.error(-1,"数据插入失败");
        }
        return R.ok();
    }

    @ApiOperation(value="广告模板更新",notes = "广告模板更新",httpMethod = "PATCH")
    @PatchMapping(value="/update")
    public R update(@RequestBody MarketTemplate marketTemplate){
        //如果更新的模板为有效状态 则判断除了改模板 其他有效模板是否存在
        if(iMarketTemplateService.getCountForUpdate(marketTemplate.getTempCategoryCode(),marketTemplate.getId())>=1 && marketTemplate.getTemplateStatus() == 1){
            return R.error(-2,"每种广告只能维护一种有效类型");
        }
        ValidatorUtils.validateEntity(marketTemplate);
        try {
            iMarketTemplateService.update(marketTemplate);
        }catch(Exception e){
            logger.error("Data update failed:{}",e.getMessage());
            return R.error(-1,"数据更新失败");
        }
        return R.ok();
    }

    @ApiOperation(value="广告模板删除",notes = "广告模板删除",httpMethod = "DELETE")
    @DeleteMapping(value="/delete/{id}")
    public R delete(@PathVariable String id){
        try {
            iMarketTemplateService.delete(id);
        }catch(Exception e){
            logger.error("Data delete failed:{}",e.getMessage());
            return R.error(-1,"数据删除失败");
        }
        return R.ok();
    }

    @ApiOperation(value="判断有效的模板类型是否已经存在",notes = "判断有效的模板类型是否已经存在",httpMethod = "GET")
    @GetMapping(value="/check/{id}")
    public R check(@PathVariable String id){
        try {
            Integer count = iMarketTemplateService.checkExist(id);
            return R.ok().put("data",count);
        }catch(Exception e){
            logger.error("Data queryCount failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

}
