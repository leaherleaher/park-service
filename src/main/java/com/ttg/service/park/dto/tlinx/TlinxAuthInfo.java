package com.ttg.service.park.dto.tlinx;

import lombok.Data;

/**
 * <p>Title: TlinxAuthInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 14:43
 */
@Data
public class TlinxAuthInfo {
    private String appId;

    private String appToken;

    private String exchangeToken;

    private String token;

    private String aesKey;

    private Integer userId;

    private String isvalid;
}
