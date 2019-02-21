package com.ttg.service.park.dto.user;

import lombok.Data;

/**
 * <p>Title: UserInfo</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 13:39
 */
@Data
public class UserInfo {
    private String userId;
    private String userName;
    private String userPwd;
    private Integer userStatus;
    private Integer userType;
    private String merchantId;
    private String merchantName;
    private String branchId;
    private String branchName;
    private String paymentTypeId;
    private String userRelation;
    private Integer paymentTypeStatus;
    private String paymentTypeName;

}
