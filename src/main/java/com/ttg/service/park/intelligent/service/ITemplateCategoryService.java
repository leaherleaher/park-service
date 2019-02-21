package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.intelligent.entity.TemplateCategory;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
public interface ITemplateCategoryService extends IService<TemplateCategory> {

    //查询所有模板类型  分页
    PageUtils queryPage();

    //查询所有模板类型 不用分页
    List<TemplateCategory> getList(String status);

    //模板类型保存
    Integer save(TemplateCategory templateCategory);

    //模板类型更新
    Integer update(TemplateCategory templateCategory);

    //模板类型删除
    Integer delete(String id);
}
