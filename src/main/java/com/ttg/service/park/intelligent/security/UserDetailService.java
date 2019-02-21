package com.ttg.service.park.intelligent.security;

//~--- non-JDK imports --------------------------------------------------------

import com.google.common.collect.Sets;
import com.ttg.service.park.intelligent.service.auth.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Set;



//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Class UserDetailService
 * Description spring security的UserDetailsService，提供给鉴权使用
 * Create 2017-03-15 14:29:15
 *
 * @author Ardy
 */
public class UserDetailService implements UserDetailsService {

    /**
     * Field LOGGER
     * Description
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailService.class);
    @Autowired
    private IAuthService authService;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    /**
     * Method loadUserByUsername
     * Description 说明：
     *
     * @param username 说明：
     * @return 返回值说明：
     * @throws UsernameNotFoundException 异常：
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("username:{}", username);
        UserPrincipal userPrincipal = authService.getPrincipal(username);

        return userPrincipal;
    }

    /**
     * Method getAuthorities
     * Description 说明：
     *
     * @return 返回值说明：
     */
    private Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = Sets.newHashSet();

        authorities.add(new SimpleGrantedAuthority("role"));

        return authorities;
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
