package com.ttg.service.park.dto.tlinx;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>Title: TlinxUserInfoResp</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:56
 */
@Data
public class TlinxUserInfoResp {

    @JSONField(name = "scr_id")
    private Integer scrId;
    @JSONField(name = "true_name")
    private String trueName;
    @JSONField(name = "roles_id")
    private String rolesId;
    @JSONField(name = "role_name")
    private String roleName;
    @JSONField(name = "shop_num")
    private Integer shopNum;
    @JSONField(name = "shop_nos")
    private String shopNos;
    @JSONField(name = "org_no")
    private String orgNo;
    @JSONField(name = "org_name")
    private String orgName;
    @JSONField(name = "mct_no")
    private Integer mctNo;
    @JSONField(name = "mct_name")
    private String mctName;
    @JSONField(name = "brand_name")
    private String brandName;
    private String logo;
    @JSONField(name = "tra_id")
    private String traId;
    @JSONField(name = "shop_no")
    private String shopNo;
    @JSONField(name = "shop_name")
    private String shopName;
    @JSONField(name = "shop_full_name")
    private String shopFullName;
    private String province;
    private String city;
    private String county;
    @JSONField(name = "cityid")
    private String cityId;
    private String address;
    private String tel;
    @JSONField(name = "open_hours")
    private String openHours;

}
