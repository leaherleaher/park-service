package com.ttg.service.park.intelligent.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ttg.service.park.intelligent.entity.MerchantBasicSetting;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttg.service.park.intelligent.entity.PayOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
public interface MerchantBasicSettingMapper extends BaseMapper<MerchantBasicSetting> {

    MerchantBasicSetting selectByMerId( @Param("ew") Wrapper<MerchantBasicSetting> wrapper);
}
