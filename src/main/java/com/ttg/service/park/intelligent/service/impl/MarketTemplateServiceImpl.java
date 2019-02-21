package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.sys.GetUserPrincipal;
import com.ttg.service.park.common.utils.util.CommonUtils;
import com.ttg.service.park.intelligent.entity.MarketTemplate;
import com.ttg.service.park.intelligent.mapper.MarketTemplateMapper;
import com.ttg.service.park.intelligent.service.IMarketTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
@Service
public class MarketTemplateServiceImpl extends ServiceImpl<MarketTemplateMapper, MarketTemplate> implements IMarketTemplateService {

    private static Logger logger = LoggerFactory.getLogger(MarketTemplateServiceImpl.class);
    @Autowired
    private MarketTemplateMapper marketTemplateMapper;
    @Override
    public List<MarketTemplate> getList() {
        //广告模板查询 新增商户过滤 每个商户只能看到自己维护的模板信息
        String merId = null;
        try {
            merId = GetUserPrincipal.getUserId().getUsername();
        }catch(Exception e){
            logger.error("get merId failure");
            return null;
        }

        return marketTemplateMapper.selectAllList(merId);
    }

    @Override
    public Integer save(MarketTemplate marketTemplate) {
        //初始化模板.vip_btn .btn为有效状态
        //marketTemplate.setTemplateStatus(1);
        //初始化代码为UUID
        marketTemplate.setTemplateNo(CommonUtils.generateUUID());

        return marketTemplateMapper.insert(marketTemplate);
    }



    @Override
    public Integer update(MarketTemplate marketTemplate) {
        return marketTemplateMapper.updateById(marketTemplate);
    }

    @Override
    public Integer delete(String id) {
        return marketTemplateMapper.deleteById(id);
    }

    @Override
    public MarketTemplate selectById(String id) {
        return marketTemplateMapper.selectById(id);
    }

    @Override
    public List<MarketTemplate> getEnList(String merId) {
        return marketTemplateMapper.selectEnList(merId);

    }

    @Override
    public Integer getCount(String categoryCode) {
        //广告模板查询 新增商户过滤 每个商户只能看到自己维护的模板信息
        return marketTemplateMapper.selectCount(
                new Wrapper<MarketTemplate>() {
                    @Override
                    public String getSqlSegment() {
                        String merId = null;
                        try {
                            merId = GetUserPrincipal.getUserId().getUsername();
                        }catch(Exception e){
                            logger.error("get merId failure");
                            throw new BusiException("merId get filure");
                        }
                        return "where template_status = 1 and temp_category_code = "+categoryCode + " and create_by = "+ "'"+ merId +"'";
                    }
                }
        );
    }

    @Override
    public Integer getCountForUpdate(String categoryCode, String id) {
        return marketTemplateMapper.selectCount(
                new Wrapper<MarketTemplate>() {
                    @Override
                    public String getSqlSegment() {
                        String merId = null;
                        try {
                            merId = GetUserPrincipal.getUserId().getUsername();
                        }catch(Exception e){
                            logger.error("get merId failure");
                            throw new BusiException("merId get filure");
                        }
                        return "where template_status = 1 and id <> '"+id +"' and temp_category_code = "+categoryCode + " and create_by = "+ "'"+ merId +"'";
                    }
                }
        );
    }

    @Override
    public Integer checkExist(String id) {
        //广告模板查询 新增商户过滤 每个商户只能看到自己维护的模板信息
        final String merId = GetUserPrincipal.getUserId().getUsername();

        return marketTemplateMapper.selectCount(
                new Wrapper<MarketTemplate>() {
                    @Override
                    public String getSqlSegment() {
                        return "where template_status = 1 and template_id = '"+id +"'" + " and create_by = '"+ merId +"'";
                    }
                }
        );
    }
}
