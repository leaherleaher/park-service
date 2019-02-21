package com.ttg.service.park.intelligent.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Optional;
import com.ttg.service.park.intelligent.entity.PSysBranch;
import com.ttg.service.park.intelligent.mapper.PSysBranchMapper;
import com.ttg.service.park.intelligent.service.IPSysBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机构信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PSysBranchServiceImpl extends ServiceImpl<PSysBranchMapper, PSysBranch> implements IPSysBranchService {

    @Autowired
    private PSysBranchMapper pSysBranchMapper;
    @Override
    public void verifyBranch(PSysBranch branch) {
        PSysBranch tmp = pSysBranchMapper.selectByBranchId(branch.getBranchId());
        if (!Optional.fromNullable(tmp).isPresent()){
            pSysBranchMapper.insert(branch);
        }else {
            pSysBranchMapper.updateById(tmp);
        }
    }
}
