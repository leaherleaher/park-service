package com.ttg.service.park.dto.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: TlinxReqParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 16:46
 */
@Data
public class TlinxReqParam implements Serializable {
    private String openId;
    private String openKey;
    private String status;
    private String token;
    private String aesKey;
}
