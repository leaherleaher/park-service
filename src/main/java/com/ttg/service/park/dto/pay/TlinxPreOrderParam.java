package com.ttg.service.park.dto.pay;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <p>Title: TlinxPreOrderParam</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/12 18:02
 */
public class TlinxPreOrderParam  extends BaseVoParameters implements Serializable {

    @JSONField(name = "out_no")
    private String outNo;
    @JSONField(name = "pmt_tag")
    private String pmtTag;
    @JSONField(name = "pmt_name")
    private String pmtName;
    @JSONField(name = "ord_name")
    private String ordName;
    @JSONField(name = "original_amount")
    private String originalAmount;
    @JSONField(name = "discount_amount")
    private String discountAmount;
    @JSONField(name = "ignore_amount")
    private String ignoreAmount;
    @JSONField(name = "trade_amount")
    private String tradeAmount;
    @JSONField(name = "trade_account")
    private String tradeAccount;
    @JSONField(name = "trade_no")
    private String tradeNo;
    @JSONField(name = "trade_result")
    private String tradeResult;
    private String remark;
    @JSONField(name = "auth_code")
    private String authCode;
    private String tag;
    @JSONField(name = "jump_url")
    private String jumpUrl;
    @JSONField(name = "notify_url")
    private String notifyUrl;


    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
        this.allParameters.put("out_no",outNo);
    }

    public String getPmtTag() {
        return pmtTag;
    }

    public void setPmtTag(String pmtTag) {
        this.pmtTag = pmtTag;
        this.allParameters.put("pmt_tag",pmtTag);
    }

    public String getPmtName() {
        return pmtName;
    }

    public void setPmtName(String pmtName) {
        this.pmtName = pmtName;
        this.allParameters.put("pmt_name",pmtName);
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
        this.allParameters.put("ord_name",ordName);
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
        this.allParameters.put("original_amount",originalAmount);
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
        this.allParameters.put("discount_amount",discountAmount);
    }

    public String getIgnoreAmount() {
        return ignoreAmount;
    }

    public void setIgnoreAmount(String ignoreAmount) {
        this.ignoreAmount = ignoreAmount;
        this.allParameters.put("ignore_amount",ignoreAmount);
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
        this.allParameters.put("trade_amount",tradeAmount);
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
        this.allParameters.put("trade_account",tradeAccount);
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        this.allParameters.put("trade_no",tradeNo);
    }

    public String getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(String tradeResult) {
        this.tradeResult = tradeResult;
        this.allParameters.put("trade_result",tradeResult);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        this.allParameters.put("remark",remark);
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
        this.allParameters.put("auth_code",authCode);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
        this.allParameters.put("tag",tag);
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
        this.allParameters.put("jump_url",jumpUrl);
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    @Override
    public String toString() {
        return "TTlxWxRechargePreOrderParam{" +
                "outNo='" + outNo + '\'' +
                ", pmtTag='" + pmtTag + '\'' +
                ", pmtName='" + pmtName + '\'' +
                ", ordName='" + ordName + '\'' +
                ", originalAmount='" + originalAmount + '\'' +
                ", discountAmount='" + discountAmount + '\'' +
                ", ignoreAmount='" + ignoreAmount + '\'' +
                ", tradeAmount='" + tradeAmount + '\'' +
                ", tradeAccount='" + tradeAccount + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeResult='" + tradeResult + '\'' +
                ", remark='" + remark + '\'' +
                ", authCode='" + authCode + '\'' +
                ", tag='" + tag + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        this.allParameters.put("notify_url",notifyUrl);
    }
}
