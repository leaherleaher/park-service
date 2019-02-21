package com.ttg.service.park.intelligent.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttg.service.park.intelligent.entity.PSysBranch;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 机构信息表 Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
public interface PSysBranchMapper extends BaseMapper<PSysBranch> {

    PSysBranch selectByBranchId(@Param("branchId") String branchId);
}
