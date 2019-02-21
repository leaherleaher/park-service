package com.ttg.service.park.intelligent.service;

import com.ttg.service.park.intelligent.entity.PTlinxSecurity;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * T-Linx商户令牌信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPTlinxSecurityService extends IService<PTlinxSecurity> {

    PTlinxSecurity findByShopId(int shopId);
}
