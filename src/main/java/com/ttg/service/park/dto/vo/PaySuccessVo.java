package com.ttg.service.park.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Title: PaySuccessVo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-29 20:53
 */
@Data
public class PaySuccessVo {

    private String parkName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entryTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;

    private Integer stopTime;

    private Integer payable;

    private Integer orderAmt;

    private String plateNo;

    private String merLogo;

    private Integer countdownTime;

    private String merId;


}
