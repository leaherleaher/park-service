package com.ttg.service.park.dto.pay;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <p>Title: TlinxShopPayTypeReqParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-13 9:50
 */
public class TlinxShopPayTypeReqParam extends BaseVoParameters implements Serializable {

    @JSONField(name = "pmt_type")
    private String pmtType;

    public String getPmtType() {
        return pmtType;
    }

    public void setPmtType(String pmtType) {
        this.pmtType = pmtType;
        this.allParameters.put("pmt_type",pmtType);
    }

}
