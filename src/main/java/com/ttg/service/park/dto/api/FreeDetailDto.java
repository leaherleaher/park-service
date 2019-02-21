package com.ttg.service.park.dto.api;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: FreeDetailDto</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2019-01-02 16:21
 */
@Data
public class FreeDetailDto implements Serializable {
    private Integer type;
    private Integer money;
    private Long time;
    private String code;
}
