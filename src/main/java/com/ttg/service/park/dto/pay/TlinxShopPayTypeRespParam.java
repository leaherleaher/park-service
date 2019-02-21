package com.ttg.service.park.dto.pay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: TlinxShopPayTypeRespParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-13 9:51
 */
@Data
public class TlinxShopPayTypeRespParam implements Serializable {

    @JSONField(name = "pmt_name")
    private String pmtName;
    @JSONField(name = "pmt_type")
    private String pmtType;

    @JSONField(name = "pmt_icon")
    private String pmtIcon;
    @JSONField(name = "pmt_tag")
    private String pmtTag;

}
