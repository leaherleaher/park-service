package com.ttg.service.park.intelligent.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>Title: UserPrincipal</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 16:57
 */
@Data
public class UserPrincipal implements UserDetails, Serializable {

    private String branchId;

    private String merchantId;

    private String accountManagerId;

    private String paymentTypeId;

    private String openId;

    private String nickName;

    private String merchantName;

    private String branchName;

    /**
     * Field userName
     * Description
     */
    private String userName;

    /**
     * Field passWord
     * Description
     */
    private String userPwd;

    /**
     * Field userPhone
     * Description
     */
    private String userPhone;

    /**
     * Field userTel
     * Description
     */
    private String userTel;

    /**
     * Field userAddress
     * Description
     */
    private String userAddress;

    /**
     * Field email
     * Description
     */
    private String email;

    /**
     * Field isActive
     * Description
     */
    private boolean isActive;

    /**
     * Field isExpired
     * Description
     */
    private boolean isExpired;

    /**
     * Field createUser
     * Description
     */
    private String createUser;

    private Integer paymentTypeStatus;

    /**
     * Field authorities
     * Description
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Method equals
     * Description 说明：
     *
     * @param rhs 说明：
     *
     * @return 返回值说明：
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof UserPrincipal) {
            return userName.equals(( (UserPrincipal) rhs ).userName);
        }

        return false;
    }

    /**
     * Method hashCode
     * Description 说明：
     *
     * @return 返回值说明：
     */
    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
