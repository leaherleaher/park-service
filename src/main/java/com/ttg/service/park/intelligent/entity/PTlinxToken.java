package com.ttg.service.park.intelligent.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * T-Linx应用令牌信息表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Data
//@Accessors(chain = true)
@TableName("p_tlinx_token")
public class PTlinxToken extends Model<PTlinxToken> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private String appId;

    /**
     * 应用令牌
     */
    private String token;

    /**
     * AES加密密钥
     */
    @TableField("aes_key")
    private String aesKey;

    /**
     * 过期时长(秒)
     */
    @TableField("expired_time")
    private Integer expiredTime;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("expired_date")
    private Date expiredDate;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("modify_time")
    private Date modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
