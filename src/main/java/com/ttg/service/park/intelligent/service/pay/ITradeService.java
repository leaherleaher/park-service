package com.ttg.service.park.intelligent.service.pay;

import com.ttg.service.park.dto.pay.PlaceOrderInfo;
import com.ttg.service.park.dto.pay.TradeInfo;
import com.ttg.service.park.dto.pay.TradeUserPayReq;

/**
 * <p>Title: ITradeService</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 15:49
 */
public interface ITradeService {

    TradeInfo getUserTradeInfo(String merchantId, String paymentCode, String scode );

    PlaceOrderInfo handleUserPay(TradeUserPayReq tradeUserPayReq);
}
