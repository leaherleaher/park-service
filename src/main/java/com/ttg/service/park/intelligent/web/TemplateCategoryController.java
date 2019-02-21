package com.ttg.service.park.intelligent.web;


import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.common.validator.ValidatorUtils;
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
@RequestMapping("/merchant/Advertisement/category")
public class TemplateCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateCategoryController.class);

    @Autowired
    private ITemplateCategoryService iTemplateCategoryService;

    @ApiOperation(value="广告类型清单",notes = "广告类型清单",httpMethod = "GET")
    @GetMapping(value="/list")
    public R getList(@RequestParam Map<String,String> map){
        String status = map.get("status");
        try {
            List<TemplateCategory> list = iTemplateCategoryService.getList(status);
            return R.ok().put("data",list);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }


    }

    @ApiOperation(value="广告类型详情",notes = "广告类型详情",httpMethod = "GET")
    @GetMapping(value="/info/{id}")
    public R info(@PathVariable("id") String id){
        List<TemplateCategory> list = null;
        try {
            TemplateCategory templateCategory = iTemplateCategoryService.selectById(id);
            return R.ok().put("data",templateCategory);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }



    @ApiOperation(value="广告类型新增",notes = "广告类型新增",httpMethod = "POST")
    @PostMapping(value="/add")
    public R save(@RequestBody TemplateCategory templateCategory){
        ValidatorUtils.validateEntity(templateCategory);
        try {
            iTemplateCategoryService.save(templateCategory);
        }catch(Exception e){
            logger.error("Data insert failed:{}",e.getMessage());
            return R.error(-1,"数据插入失败");
        }
        return R.ok();
    }

    @ApiOperation(value="广告类型更新",notes = "广告类型更新",httpMethod = "PATCH")
    @PatchMapping(value="/update")
    public R update(@RequestBody TemplateCategory templateCategory){
        ValidatorUtils.validateEntity(templateCategory);
        try {
            iTemplateCategoryService.update(templateCategory);
        }catch(Exception e){
            logger.error("Data update failed:{}",e.getMessage());
            return R.error(-1,"数据更新失败");
        }
        return R.ok();
    }

    @ApiOperation(value="广告类型删除",notes = "广告类型删除",httpMethod = "DELETE")
    @DeleteMapping(value="/delete/{id}")
    public R delete(@PathVariable String id){
        try {
            iTemplateCategoryService.delete(id);
        }catch(Exception e){
            logger.error("Data delete failed:{}",e.getMessage());
            return R.error(-1,"数据删除失败");
        }
        return R.ok();
    }

}
