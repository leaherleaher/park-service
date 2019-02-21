package com.ttg.service.park.dto.tlinx;

import lombok.Data;

/**
 * <p>Title: TlinxAppTokenInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:34
 */
@Data
public class TlinxAppTokenInfo {
    private String appToken;
    private String appId;
    private String aesKey;
    private String data;
}
