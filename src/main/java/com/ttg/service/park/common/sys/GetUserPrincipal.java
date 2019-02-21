package com.ttg.service.park.common.sys;

import com.ttg.service.park.intelligent.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <p>Title: GetUserPrincipal</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/10 15:33
 */
public class GetUserPrincipal {

    public static UserPrincipal getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = null;
        Object principal = null;
        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal != null && principal instanceof UserPrincipal) {
            userPrincipal = (UserPrincipal) principal;
        }
        return userPrincipal;
    }
}
