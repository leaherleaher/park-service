package com.ttg.service.park.dto.pay;

/**
 * <p>Title: BaseVoParameters</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-13 9:49
 */

import com.alibaba.fastjson.annotation.JSONField;

import java.util.TreeMap;
public class BaseVoParameters {
    @JSONField(name = "open_id")
    private String openId;
    private String timestamp;
    @JSONField(name = "open_key")
    private String openKey;

    @JSONField(serialize = false)
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.allParameters.put("data",data);
    }

    public String getOpenKey() {
        return openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
        this.allParameters.put("open_key",openKey);
    }

    @JSONField(serialize = false)
    private String sign;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
        this.allParameters.put("open_id",openId);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        this.allParameters.put("timestamp",timestamp);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
        this.allParameters.put("sign",sign);
    }

    /**
     * Field allParameters
     * Description
     */
    @JSONField(serialize = false)
    protected final TreeMap<String, String> allParameters = new TreeMap();

    /**
     * Method getAllParameters
     * Description
     *
     * @return Map<String,String>
     */
    public TreeMap<String, String> getAllParameters() {
        return allParameters;
    }
}
