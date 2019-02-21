package com.ttg.service.park.intelligent.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Data
//@Accessors(chain = true)
@TableName("merchant_basic_setting")
public class MerchantBasicSetting extends Model<MerchantBasicSetting> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;


    /**
     * 商户id
     */
    @TableField("mer_id")
    private String merId;

    /**
     * 商户logo
     */
    @TableField("mer_logo")
    private String merLogo;

    /**
     * 倒计时时长（分）
     */
    @TableField("countdown_time")
    private Integer countdownTime;

    /**
     * url1
     */
    @TableField("attr1_url")
    private String attr1Url;

    /**
     * url2
     */
    @TableField("attr2_url")
    private String attr2Url;

    /**
     * url3
     */
    @TableField("attr3_url")
    private String attr3Url;

    /**
     * url4
     */
    @TableField("attr4_url")
    private String attr4Url;

    /**
     * url5
     */
    @TableField("attr5_url")
    private String attr5Url;

    /**
     * 创建人
     */
    @TableField(value="create_by",fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="create_time",fill=FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新人
     */
    @TableField(value="last_updated_by",fill=FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="last_updated_time",fill=FieldFill.INSERT_UPDATE)
    private Date lastUpdatedTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
