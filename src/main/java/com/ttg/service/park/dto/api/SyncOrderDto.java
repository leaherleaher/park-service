package com.ttg.service.park.dto.api;

import lombok.Data;


/**
 * <p>Title: SyncOrderDto</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2019-01-02 16:19
 */
@Data
public class SyncOrderDto {
    private String orderNo;
    private Integer amount;
    private Integer payType;
    private Integer payMethod;
    private Integer freeMoney;
    private Integer freeTime;
    private FreeDetailDto[] freeDetail;
}
