package com.ttg.service.park.intelligent.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;




import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * <p>
 * 缴费订单表
 * </p>
 *
 * @author YangTao
 * @since 2018-12-12
 */
@Data
//@Accessors(chain = true)
@TableName("p_c_order")
public class PCOrder extends Model<PCOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 缴费金额
     */
    @TableField("order_amt")
    private Integer orderAmt;

    /**
     * 下单时间
     */
    @TableField("order_time")
    private Date orderTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private Date payTime;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 支付方式类型
     */
    @TableField("pay_id")
    private Integer payId;

    /**
     * 平台订单号
     */
    @TableField("p_order_id")
    private String pOrderId;

    /**
     * 身份标识
     */
    private String scode;

    /**
     * 商户标识
     */
    @TableField("mer_id")
    private String merId;

    /**
     * @Description //TODO 缴费成功 停车场缴费明细同步状态 0未同步  1 已同步
     * @Param
     * @return
     **/
    @TableField("sync_status")
    private Integer syncStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="create_time",fill= FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="last_updated_time",fill=FieldFill.INSERT_UPDATE)
    private Date lastUpdatedTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
