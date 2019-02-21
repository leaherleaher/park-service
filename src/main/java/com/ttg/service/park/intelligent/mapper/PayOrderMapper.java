package com.ttg.service.park.intelligent.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttg.service.park.dto.vo.PaySuccessVo;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YangTao
 * @since 2018-12-10
 */
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    List<PayOrder> selectPlateNoList(Page<PayOrder> page, @Param("ew") Wrapper<PayOrder> wrapper);

    List<PayOrder> selectPlateNoInfo(Page<PayOrder> page, Wrapper<PayOrder> wrapper);

    PayOrder findDetailInfo(String scode);

    PaySuccessVo findSuccessInfo(@Param("orderNo") String orderNo);
}
