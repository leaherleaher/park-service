package com.ttg.service.park.intelligent.security;

//~--- non-JDK imports --------------------------------------------------------

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Class AuthenticationSuccessHandler
 * Description 鉴权成功处理
 * Create 2017-03-13 16:46:21
 *
 * @author Ardy
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Field LOGGER
     * Description
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    /**
     * Method onAuthenticationSuccess
     * Description 说明：登录成功后操作
     *
     * @param request        说明：
     * @param response       说明：
     * @param authentication 说明：
     * @throws IOException      异常：
     * @throws ServletException 异常：
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        LOGGER.info("用户[{}]登录成功", principal.getUsername());
        super.setDefaultTargetUrl("/merchant/index");
        super.setAlwaysUseDefaultTargetUrl(true);
        super.onAuthenticationSuccess(request, response, authentication);

    }
}


//~ Formatted by Jindent --- http://www.jindent.com
