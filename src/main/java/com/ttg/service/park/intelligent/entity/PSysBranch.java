package com.ttg.service.park.intelligent.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机构信息表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Data
//@Accessors(chain = true)
@TableName("p_sys_branch")
public class PSysBranch extends Model<PSysBranch> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;


    /**
     * 机构标识
     */
    @TableField("branch_id")
    private String branchId;

    /**
     * 机构名称
     */
    @TableField("branch_name")
    private String branchName;

    /**
     * 机构描述
     */
    @TableField("branch_desc")
    private String branchDesc;

    /**
     * 上级机构标识
     */
    @TableField("par_branch_id")
    private String parBranchId;

    /**
     * 机构级别
     */
    @TableField("branch_level")
    private Integer branchLevel;

    /**
     * 机构顺序
     */
    @TableField("branch_order")
    private Integer branchOrder;

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
