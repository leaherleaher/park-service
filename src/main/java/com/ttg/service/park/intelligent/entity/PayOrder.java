package com.ttg.service.park.intelligent.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ttg.service.park.common.validator.group.AddGroup;
import com.ttg.service.park.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangTao
 * @since 2018-12-10
 */
@Data
//@Accessors(chain = true)
@TableName("pay_order")
public class PayOrder extends Model<PayOrder> {

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
     * openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 车牌号
     */
    @NotBlank(message = "用户名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @TableField("plate_no")
    private String plateNo;

    /**
     * 车场id
     */
    @NotNull(message = "车场id不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @TableField("park_id")
    private Integer parkId;

    /**
     * 订单号
     */
    @NotNull(message = "订单号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @TableField("order_no")
    private String orderNo;

    /**
     * 停车场名称
     */
    @TableField("park_name")
    private String parkName;

    /**
     * 入场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("entry_time")
    private Date entryTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("pay_time")
    private Date payTime;

    /**
     * 停车时长
     */
    @TableField("elapsed_time")
    private Integer elapsedTime;

    /**
     * 停车入场图片
     */
    private String img;

    /**
     * 应付金额
     */
    private Integer payable;

    /**
     * 缴费后允许的延迟出场时间
     */
    @TableField("delay_time")
    private Integer delayTime;
    
    /**
     * @Description //TODO 出场时间
     * @Param []
     * @return java.io.Serializable
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("out_time")
    private Date outTime;
    
    /**
     * @Description //TODO 支付时间
     * @Param []
     * @return java.io.Serializable
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("order_time")
    private Date orderTime;

    /**
     * @Description //TODO 支付状态
     * @Param []
     * @return java.io.Serializable
     **/
    @TableField("pay_status")
    private Integer payStatus;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
