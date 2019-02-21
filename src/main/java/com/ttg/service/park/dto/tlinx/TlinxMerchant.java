package com.ttg.service.park.dto.tlinx;

import lombok.Data;

/**
 * <p>Title: TlinxMerchant</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 14:01
 */
@Data
public class TlinxMerchant {
    private String merchantId;
    private String merchantName;
    private String branchId;
    private String branchName;
    private String cityId;
    private String appId;
    private String aesKey;
    private String token;
    private String cityName;
    private String shopId;
    private String logo;
}
