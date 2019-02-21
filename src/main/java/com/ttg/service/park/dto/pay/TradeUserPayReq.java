package com.ttg.service.park.dto.pay;

import com.ttg.service.park.intelligent.entity.PayOrder;
import lombok.Data;

/**
 * <p>Title: TradeUserPayReq</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 15:44
 */
@Data
public class TradeUserPayReq extends PayOrder {


    private String callbackUri;
    private String notifyUrl;
    private String payTypeCode;
    private String imgName;


}
