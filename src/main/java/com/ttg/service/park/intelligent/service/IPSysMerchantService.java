package com.ttg.service.park.intelligent.service;


import com.baomidou.mybatisplus.service.IService;
import com.ttg.service.park.intelligent.entity.PSysMerchant;

/**
 * <p>
 * 商户基础信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPSysMerchantService extends IService<PSysMerchant> {

    //校验商户和机构信息并保存
    void verifyMerchant(PSysMerchant merchant);
}
