package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Optional;
import com.ttg.service.park.intelligent.entity.PSysMerchant;
import com.ttg.service.park.intelligent.mapper.PSysMerchantMapper;
import com.ttg.service.park.intelligent.service.IPSysMerchantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户基础信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PSysMerchantServiceImpl extends ServiceImpl<PSysMerchantMapper, PSysMerchant> implements IPSysMerchantService {

    @Autowired
    private PSysMerchantMapper pSysMerchantMapper;

    @Override
    public void verifyMerchant(PSysMerchant merchant) {
        PSysMerchant tmp = pSysMerchantMapper.selectByMerId(merchant.getMerId());
        if (!Optional.fromNullable(tmp).isPresent()) {
            pSysMerchantMapper.insert(merchant);
        } else {
            if (StringUtils.isEmpty(tmp.getShopId())) {
                tmp.setShopId(merchant.getShopId());
            }
            tmp.setLogo(merchant.getLogo());
            pSysMerchantMapper.updateById(tmp);

        }
    }
}
