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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * T-Linx应用授权信息表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Data
//@Accessors(chain = true)
@TableName("p_tlinx_auth")
public class PTlinxAuth extends Model<PTlinxAuth> {

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
     * 用户标识令牌
     */
    @TableField("app_token")
    private String appToken;

    /**
     * 交换令牌
     */
    @TableField("exchange_token")
    private String exchangeToken;

    /**
     * 令牌
     */
    private String token;

    /**
     * AES加密密钥
     */
    @TableField("aes_key")
    private String aesKey;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 是否有效(1是0否)
     */
    private String isvalid;

    /**
     * 创建日期
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
