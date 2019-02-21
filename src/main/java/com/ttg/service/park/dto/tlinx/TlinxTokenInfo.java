package com.ttg.service.park.dto.tlinx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * <p>Title: TlinxTokenInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 15:19
 */
@Data
public class TlinxTokenInfo {
    @JSONField(name = "token")
    private String token;
    @JSONField(name = "aes_key")
    private String aesKey;
    @JSONField(name = "expired_time")
    private Integer expiredTime;
    private String appId;
    private Date expiredDate;
}
