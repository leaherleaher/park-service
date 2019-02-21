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
import org.jboss.logging.Field;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangTao
 * @since 2018-12-11
 */
@Data
//@Accessors(chain = true)
@TableName("market_template")
public class MarketTemplate extends Model<MarketTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 模板类型id
     */
    @TableField("template_id")
    private String templateId;

    /**
     * 模板类型代码
     */
    @TableField("temp_category_code")
    private String tempCategoryCode;

    /**
     * 模板编号
     */
    @TableField("template_no")
    private String templateNo;

    /**
     * 模板名称
     */
    @TableField("template_name")
    private String templateName;

    /**
     * 模板渲染url
     */
    @TableField("template_url")
    private String templateUrl;

    /**
     * 模板图片
     */
    @TableField("template_img")
    private String templateImg;

    /**
     * 模板状态 0-禁用 1-启用
     */
    @TableField("template_status")
    private Integer templateStatus;

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

    /**
     * 备注
     */
    private String note;

    /**
     * @Description //TODO 新增查询字段
     * @Param []
     * @return java.io.Serializable
     **/
    @TableField(exist = false)
    private String tempCategoryDesc;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
