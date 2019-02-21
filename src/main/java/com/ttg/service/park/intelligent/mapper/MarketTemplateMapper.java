package com.ttg.service.park.intelligent.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ttg.service.park.intelligent.entity.MarketTemplate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
public interface MarketTemplateMapper extends BaseMapper<MarketTemplate> {

    List<MarketTemplate> selectAllList(@Param("merId") String merId);

    MarketTemplate selectById(@Param("id") String id);

    List<MarketTemplate> selectEnList(@Param("merId") String merId);
}
