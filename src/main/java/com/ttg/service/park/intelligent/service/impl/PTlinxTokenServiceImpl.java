package com.ttg.service.park.intelligent.service.impl;

import com.ttg.service.park.intelligent.entity.PTlinxToken;
import com.ttg.service.park.intelligent.mapper.PTlinxTokenMapper;
import com.ttg.service.park.intelligent.service.IPTlinxTokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * T-Linx应用令牌信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PTlinxTokenServiceImpl extends ServiceImpl<PTlinxTokenMapper, PTlinxToken> implements IPTlinxTokenService {

    @Autowired
    private PTlinxTokenMapper pTlinxTokenMapper;

    @Override
    public PTlinxToken findByAppId(String appId) {
        return pTlinxTokenMapper.findByAppId(appId);
    }
}
