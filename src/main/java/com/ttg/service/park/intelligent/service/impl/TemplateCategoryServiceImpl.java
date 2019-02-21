package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ttg.service.park.common.utils.util.CommonUtils;
import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.ttg.service.park.intelligent.entity.TemplateCategory;
import com.ttg.service.park.intelligent.mapper.TemplateCategoryMapper;
import com.ttg.service.park.intelligent.service.ITemplateCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
@Service
public class TemplateCategoryServiceImpl extends ServiceImpl<TemplateCategoryMapper, TemplateCategory> implements ITemplateCategoryService {

    @Autowired
    private TemplateCategoryMapper templateCategoryMapper;


    @Override
    public PageUtils queryPage() {
        return null;
    }

    //查询
    @Override
    public List<TemplateCategory> getList(String status) {
        Wrapper<TemplateCategory> ew = new EntityWrapper<>();
        ew.eq(StringUtils.isNotBlank(status),"template_status",status);
        return templateCategoryMapper.selectList(ew);
    }

    //新增
    @Override
    public Integer save(TemplateCategory templateCategory) {
        //初始化类型为有效状态 0-无效 1-有效
        templateCategory.setTemplateStatus(1);
        //初始化代码为UUID
        templateCategory.setTempCategoryCode(CommonUtils.generateUUID());
        return templateCategoryMapper.insert(templateCategory);
    }

    //更新
    @Override
    public Integer update(TemplateCategory templateCategory) {
        return templateCategoryMapper.updateById(templateCategory);
    }

    //删除
    @Override
    public Integer delete(String id) {
        return templateCategoryMapper.deleteById(id);
    }
}
