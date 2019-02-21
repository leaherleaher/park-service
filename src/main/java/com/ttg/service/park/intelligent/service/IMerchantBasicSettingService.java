package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.intelligent.entity.MerchantBasicSetting;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
public interface IMerchantBasicSettingService extends IService<MerchantBasicSetting> {

    Integer update(MerchantBasicSetting merchantBasicSetting);

    MerchantBasicSetting selectByMerId(String merId);
}
