package com.ttg.service.park.intelligent.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商户基础信息表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Data
//@Accessors(chain = true)
@TableName("p_sys_merchant")
public class PSysMerchant extends Model<PSysMerchant> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 商户号
     */
    @TableField("mer_id")
    private String merId;

    /**
     * 商户名称
     */
    @TableField("mer_name")
    private String merName;

    /**
     * 商户状态
     */
    private Integer status;

    /**
     * 机构标识
     */
    @TableField("branch_id")
    private String branchId;

    /**
     * 门店编号
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 商户LOGO
     */
    private String logo;

    /**
     * 所属城市
     */
    @TableField("city_id")
    private String cityId;

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
