package com.ttg.service.park.intelligent.security;

//~--- non-JDK imports --------------------------------------------------------

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Class AuthenticationFailureHandler
 * Description 鉴权失败处理
 * Create 2017-03-15 14:31:25
 * @author Ardy
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Field LOGGER
     * Description
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureHandler.class);

    /**
     * Method onAuthenticationFailure
     * Description 说明：
     *
     * @param request 说明：
     * @param response 说明：
     * @param exception 说明：
     *
     * @throws IOException 异常：
     * @throws ServletException 异常：
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        LOGGER.info("用户[{}]登录失败,失败原因[{}]", request.getParameter("username"), exception.getMessage());
        super.setDefaultFailureUrl("/auth/login?error=true");
        super.setUseForward(true);
        super.onAuthenticationFailure(request, response, exception);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
