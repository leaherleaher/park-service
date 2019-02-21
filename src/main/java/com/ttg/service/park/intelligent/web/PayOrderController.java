package com.ttg.service.park.intelligent.web;


import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.utils.util.PageUtils;
import com.ttg.service.park.config.properties.ParkProperties;
import com.ttg.service.park.dto.constant.BusinessConstant;
import com.ttg.service.park.dto.vo.PaySuccessVo;
import com.ttg.service.park.intelligent.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YangTao
 * @since 2018-12-10
 */
@Api("停车统计接口")
@RestController
@RequestMapping("/merchant/car")
public class PayOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    private ParkProperties parkApiProperties;

    @Autowired
    private IPayOrderService iPayOrderService;

    @ApiOperation(value="停车统计清单查询",notes = "停车统计清单查询",httpMethod = "GET")
    @GetMapping(value="/paylist")
    public R paylist(@RequestParam Map<String,Object> params){

        try {
        PageUtils page = iPayOrderService.queryPage(params);
            return R.ok().put("page", page);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

    @ApiOperation(value="停车缴费明细查询",notes = "停车缴费明细查询",httpMethod = "GET")
    @GetMapping(value="/payinfo/{id}")
    public R payinfo(@PathVariable("id") String id){

        if(!StringUtils.isEmpty(id)){
            return R.ok().put("data",iPayOrderService.selectById(id));
        }else{
            logger.error("requet parameter is null:{}",id);
            return R.error(-1, BusinessConstant.FAILURE);
        }
    }

    @ApiOperation(value="车牌清单查询",notes = "车牌清单查询",httpMethod = "GET")
    @GetMapping(value="/list")
    public R list(@RequestParam Map<String,Object> params){
        try {
            PageUtils page = iPayOrderService.queryPlateNo(params);
            return R.ok().put("page", page);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

    @ApiOperation(value="车牌号所有停车详情",notes = "车牌号所有停车详情",httpMethod = "GET")
    @GetMapping(value="/infolist")
    public R listinfo(@RequestParam Map<String,Object> params){
        try {
            PageUtils page = iPayOrderService.queryPlateNoInfo(params);
            return R.ok().put("page", page);
        }catch(Exception e){
            logger.error("Data query failed:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

    //根据订单编号查询成功支付的信息
    @ApiOperation(value="成功支付详情查询",notes = "成功支付详情查询",httpMethod = "GET")
    @GetMapping(value="/successInfo/{orderNo}")
    public R listinfo(@PathVariable String orderNo){
        try {
            PaySuccessVo payVo = iPayOrderService.findSeccessInfo(orderNo);
            return R.ok().put("data", payVo);
        }catch(Exception e){
            logger.error("pay info query failure:{}",e.getMessage());
            return R.error(-1,"数据查询失败");
        }
    }

}
