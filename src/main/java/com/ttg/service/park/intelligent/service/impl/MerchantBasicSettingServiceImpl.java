package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ttg.service.park.intelligent.entity.MerchantBasicSetting;
import com.ttg.service.park.intelligent.mapper.MerchantBasicSettingMapper;
import com.ttg.service.park.intelligent.service.IMerchantBasicSettingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Service
public class MerchantBasicSettingServiceImpl extends ServiceImpl<MerchantBasicSettingMapper, MerchantBasicSetting> implements IMerchantBasicSettingService {

    @Autowired
    private MerchantBasicSettingMapper merchantBasicSettingMapper;

    @Override
    public Integer update(MerchantBasicSetting merchantBasicSetting) {
        return merchantBasicSettingMapper.updateById(merchantBasicSetting);
    }

    @Override
    public MerchantBasicSetting selectByMerId(String merId) {
        Wrapper<MerchantBasicSetting> ew = new EntityWrapper<>();
        ew.eq(StringUtils.isNotBlank(merId),"mer_id",merId);

        return merchantBasicSettingMapper.selectByMerId(ew);
    }
}
