package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.intelligent.entity.MarketTemplate;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
public interface IMarketTemplateService extends IService<MarketTemplate> {
    //营销模板查询
    List<MarketTemplate> getList();
    //营销模板新增
    Integer save(MarketTemplate marketTemplate);
    //营销模板更新
    Integer update(MarketTemplate marketTemplate);
    //营销模板删除
    Integer delete(String id);

    MarketTemplate selectById(String id);

    List<MarketTemplate> getEnList(String merId);

    //获取有效模板数量 新增时只需要校验这个类型模板是否已经存在
    Integer getCount(String categoryCode);

    //获取有效模板数量 更新时需要判断 非当前模板  的这个类型的模板是否已经存在
    Integer getCountForUpdate(String categoryCode,String id);

    Integer checkExist(String id);
}
