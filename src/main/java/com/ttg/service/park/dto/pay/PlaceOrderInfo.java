package com.ttg.service.park.dto.pay;

import lombok.Data;

import java.math.BigDecimal;


/**
 * <p>Title: PlaceOrderInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 15:52
 */
@Data
public class PlaceOrderInfo {
    private String orderId;
    private Integer payAmt;
    private String payUrl;
    private String userCode;
    private String merchantId;
}
