package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.intelligent.entity.PTlinxToken;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * T-Linx应用令牌信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPTlinxTokenService extends IService<PTlinxToken> {

    PTlinxToken findByAppId(String appId);

}
