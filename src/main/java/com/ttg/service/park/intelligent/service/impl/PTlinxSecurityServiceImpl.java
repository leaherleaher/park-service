package com.ttg.service.park.intelligent.service.impl;

import com.ttg.service.park.intelligent.entity.PTlinxSecurity;
import com.ttg.service.park.intelligent.mapper.PTlinxSecurityMapper;
import com.ttg.service.park.intelligent.service.IPTlinxSecurityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * T-Linx商户令牌信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PTlinxSecurityServiceImpl extends ServiceImpl<PTlinxSecurityMapper, PTlinxSecurity> implements IPTlinxSecurityService {

    @Autowired
    private PTlinxSecurityMapper pTlinxSecurityMapper;
    @Override
    public PTlinxSecurity findByShopId(int shopId) {
        return pTlinxSecurityMapper.findByShopId(shopId);
    }
}
