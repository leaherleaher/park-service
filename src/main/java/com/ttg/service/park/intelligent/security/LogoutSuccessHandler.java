package com.ttg.service.park.intelligent.security;

//~--- non-JDK imports --------------------------------------------------------

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Class LogoutSuccessHandler
 * Description 登出成功后功能处理
 * Create 2017-03-15 14:31:04
 * @author Ardy
 */
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    /**
     * Field LOGGER
     * Description
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    /**
     * Method onLogoutSuccess
     * Description 说明：
     *
     * @param request 说明：
     * @param response 说明：
     * @param authentication 说明：
     *
     * @throws IOException 异常：
     * @throws ServletException 异常：
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            LOGGER.info("用户[{}]退出系统", userPrincipal.getUsername());
        }

        super.setDefaultTargetUrl("/login?logout=true");
        super.setAlwaysUseDefaultTargetUrl(true);
        super.onLogoutSuccess(request, response, authentication);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
