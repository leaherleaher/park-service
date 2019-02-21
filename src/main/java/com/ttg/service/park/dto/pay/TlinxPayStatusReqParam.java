package com.ttg.service.park.dto.pay;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <p>Title: TlinxPayStatusReqParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-13 9:52
 */
public class TlinxPayStatusReqParam extends BaseVoParameters implements Serializable {
    @JSONField(name = "ord_no")
    private String ordNo;

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
        this.allParameters.put("ord_no",ordNo);
    }

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
        this.allParameters.put("out_no",outNo);
    }

    @JSONField(name = "out_no")
    private String outNo;

}
