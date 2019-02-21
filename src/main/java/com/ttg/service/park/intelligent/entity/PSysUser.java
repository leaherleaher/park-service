package com.ttg.service.park.intelligent.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
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
 * 用户信息表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Data
//@Accessors(chain = true)
@TableName("p_sys_user")
public class PSysUser extends Model<PSysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 机构标识
     */
    @TableField("branch_id")
    private String branchId;

    /**
     * 用户状态；1：正常；0：锁定
     */
    @TableField("user_status")
    private Integer userStatus;

    /**
     * 用户类型；1：商户；0：银行管理;2:客户经理
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 所属机构或商户
     */
    @TableField("user_relation")
    private String userRelation;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="create_time")
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
