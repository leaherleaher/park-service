package com.ttg.service.park.dto.pay;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: TradeInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 15:50
 */
@Data
public class TradeInfo {
    private String merchantId;
    private String paymentCode;
    private String userCode;
    private String paymentName;
    private Integer paymentTypeStatus;
    private Integer payStatus;

    private String userFieldName;
    private String userPayAmtFieldName;
    private String pOrderId;

    private Date paymentTime;

    private Integer payAmt;

    private List<CustFieldInfo> custFieldInfos;
}
