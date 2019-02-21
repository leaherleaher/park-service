package com.ttg.service.park.intelligent.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.i18n.LocalMessageSourceService;
import com.ttg.service.park.common.utils.util.HttpUtils;
import com.ttg.service.park.common.utils.util.ParkApiUtil;
import com.ttg.service.park.common.utils.util.ParkUtil;
import com.ttg.service.park.config.properties.ParkProperties;
import com.ttg.service.park.config.properties.PayProperties;
import com.ttg.service.park.dto.constant.BusinessConstant;
import com.ttg.service.park.dto.pay.PlaceOrderInfo;
import com.ttg.service.park.dto.pay.TradeUserPayReq;
import com.ttg.service.park.dto.vo.BaseVo;
import com.ttg.service.park.intelligent.entity.PayOrder;
import com.ttg.service.park.intelligent.service.ICallbackService;
import com.ttg.service.park.intelligent.service.pay.ITradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * 缴费订单表 前端控制器
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Api("订单接口")
@Controller
@RequestMapping("/user/order")
public class PCOrderController {

    private static Logger logger = LoggerFactory.getLogger(PCOrderController.class);

    @Autowired
    private PayProperties payProperties;

    @Autowired
    private ITradeService iTradeService;

    @Autowired
    private LocalMessageSourceService messageSourceService;

    @Autowired
    private ParkProperties parkApiProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ICallbackService callbackService;

    //根据订单编号查询订单详情

    @ApiOperation(value = "订单支付", notes = "订单支付", httpMethod = "POST")
    @PostMapping(value = "/pay")
    @ResponseBody
    public R pay(@RequestBody TradeUserPayReq tradeUserPayReq, @RequestHeader("User-Agent") String userAgent) {
        //jumpUrl
        tradeUserPayReq.setCallbackUri(payProperties.getPayDomain() + "/user/order/payCall");
        //notifyUrl
        tradeUserPayReq.setNotifyUrl(payProperties.getPayDomain() + "/user/order/serverCall");
        //用户进行交易
        try {
            if (userAgent.contains("MicroMessenger")) {
                logger.debug("微信支付");
                tradeUserPayReq.setPayTypeCode("Weixin");
            } else if (userAgent.contains("AlipayClient")) {
                tradeUserPayReq.setPayTypeCode("Ali");
            } else {
                throw new BusiException("请用微信或支付宝缴费");
            }
            //tradeUserPayReq.setPayTypeCode("Weixin");
            logger.info("传入对象：tradeUserPayReq:{}",tradeUserPayReq);
            PlaceOrderInfo placeOrderInfo = iTradeService.handleUserPay(tradeUserPayReq);
            String url = placeOrderInfo.getPayUrl();
            logger.info("调用下单接口返回对象->placeOrderInfo:",placeOrderInfo);
            return R.ok().put("data", placeOrderInfo.getPayUrl());
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return R.error(-1, messageSourceService.getMessage(BusinessConstant.FAILURE));
        }
    }


    @ApiOperation(value = "车牌缴费明细查询", notes = "车牌缴费明细查询", httpMethod = "GET")
    @GetMapping(value = "/payinfo")
    @ResponseBody
    public R payinfoa(@RequestParam Map<String, Object> map) {
        //根据车牌号和停车场id去调用停车API 获取停车缴费信息
        try {
            JSONObject jsonData = ParkApiUtil.getPayDetail(map);
            if (jsonData.getJSONArray("data") != null) {
                Object data = jsonData.getJSONArray("data").get(0);
                return R.ok().put("data", data);
            } else {
                return R.error(1, "未找到停车信息");
            }
        } catch (Exception e) {
            logger.error("http request failure:{}", e.getMessage());
            return R.error(-1, "停车缴费明细信息查询失败");
        }
    }

    @RequestMapping(value="/payOrderJump",method = RequestMethod.POST)
    public ModelAndView payOrderJump(@RequestBody PayOrder payOrder,String plateId,String merId){
        ModelAndView mv = new ModelAndView();
        mv.addObject("plateId",plateId);
        mv.addObject("merId",merId);
        mv.addObject("data",payOrder);
        mv.setViewName("user/ParkMessage");
        return mv;
    }

    /**
     * @Description //TODO 用于公众号支付   支付结果跳转地址
     * @Param [status, ord_no, out_no, notice_str, sign, rand_str, timestamp]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Description //TODO jump_url为跳转地址，用户支付成功后跳转到该地址，开发者根据自身情况作相应业务处理。
     * ord_no为tlinx订单号
     * out_no为业务订单号
     * @Param [status, ord_no, out_no, notice_str, sign, rand_str, timestamp]
     **/
    @RequestMapping(value = "/payCall")
    public ModelAndView payCallBack(String status, String ord_no, String out_no, String notice_str, String sign, String rand_str, String timestamp) {

        ModelAndView mv = new ModelAndView();
        //String backUrl = null;
        if (status.equals("1")) {
//            try {
//                logger.info("status:{}", status);
//                logger.info("ord_no:{}", ord_no);
//                logger.info("out_no:{}", out_no);
//                logger.info("notice_str:{}", notice_str);
//                logger.info("sign:{}", sign);
//                logger.info("rand_str:{}", rand_str);
//                logger.info("timestamp:{}", timestamp);
//
//                callbackService.savePayBack(out_no, status,ord_no,Long.parseLong(timestamp));
//            } catch (Exception e) {
//                logger.info("向后台提交数据异常：", e.getMessage());
//            }
            //backUrl = "pay_back";
            mv.addObject("orderNo", out_no);
            mv.setViewName("user/PaySuccess");

        } else {
            mv.addObject("orderNo", out_no);
            mv.setViewName("user/PayFail");
        }
        logger.info("<<<<<<<<<<<<<<<<<<<<tlinx 回调:{}", status);

        return mv;
    }


    /**
     * @return java.lang.String
     * @Description //TODO 开发者在notify_url地址返回notify_success表示回调成功，其他信息系统还会定期回调，
     * 超过24小时，回调任务中止，可以是http、https或者ip开头的公网能访问的地址，可以加端口号。
     * @Param [out_no, ord_no, status, timestamp, sign, rand_str]
     **/
    @ResponseBody
    @RequestMapping(value = "/serverCall")
    public String serverCall(String out_no, String ord_no, String status, String timestamp, String sign, String rand_str) {


        logger.info("服务器回调out_no:{}", out_no);
        logger.info("服务器回调ord_no:{}", ord_no);
        logger.info("服务器回调status:{}", status);
        logger.info("服务器回调timestamp:{}", timestamp);
        logger.info("服务器回调sign:{}", sign);
        logger.info("服务器回调rand_str:{}", rand_str);

        //status==1 支付成功  status==4 取消支付
        //支付成功异步通知回调函数  向后台提交数据
        try {
            callbackService.savePayBack(out_no, status, ord_no, Long.parseLong(timestamp));
            return "notify_success";
        } catch (Exception e) {
            logger.error("支付成功之后，后台同步订单数据异常");
        }
        return "";
    }

}
