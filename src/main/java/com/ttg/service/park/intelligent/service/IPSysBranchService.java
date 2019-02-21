package com.ttg.service.park.intelligent.service;


import com.baomidou.mybatisplus.service.IService;
import com.ttg.service.park.intelligent.entity.PSysBranch;

/**
 * <p>
 * 机构信息表 服务类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface IPSysBranchService extends IService<PSysBranch> {
    void verifyBranch(PSysBranch branch);
}
