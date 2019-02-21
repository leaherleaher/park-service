package com.ttg.service.park.dto.pay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * <p>Title: TlinxPayStatusRespParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 17:30
 */
@Data
public class TlinxPayStatusRespParam {
    @JSONField(name = "pmt_name")
    private String pmtName;
    @JSONField(name = "pmt_tag")
    private String pmtTag;
    @JSONField(name = "ord_mct_id")
    private Integer ordMctId;
    @JSONField(name = "ord_shop_id")
    private Integer ordShopId;
    @JSONField(name = "ord_name")
    private String ordName;
    @JSONField(name = "add_time")
    private Date addTime;
    @JSONField(name = "ord_no")
    private String ordNo;
    @JSONField(name = "ord_type")
    private Integer ordType;
    @JSONField(name = "original_amount")
    private Integer originalAmount;
    @JSONField(name = "discount_amount")
    private Integer discountAmount;
    @JSONField(name = "ignore_amount")
    private Integer ignoreAmount;
    @JSONField(name = "trade_account")
    private String tradeAccount;
    @JSONField(name = "trade_no")
    private String tradeNo;
    @JSONField(name = "trade_amount")
    private Integer tradeAmount;
    @JSONField(name = "trade_qrcode")
    private String tradeQrcode;
    @JSONField(name = "trade_pay_time")
    private Date tradePayTime;
    @JSONField(name = "status")
    private Integer status;
    @JSONField(name = "trade_result")
    private String tradeResult;
    @JSONField(name = "out_no")
    private String outNo;

}
