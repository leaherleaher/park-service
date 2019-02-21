package com.ttg.service.park.intelligent.security;

//~--- non-JDK imports --------------------------------------------------------

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Class        AuthenticationFilter
 * Description  重写表单登录过滤器
 * Create       2015.08.26 at 00:56:33 CST
 *
 * @author Ardy
 */

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Field logger
     * Description
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    /**
     * Field USERNAME
     * Description
     */
    private static final String USERNAME = "username";

    /**
     * Field PASSWORD
     * Description
     */
    private static final String PASSWORD = "password";

    /**
     * Field postOnly
     * Description
     */
    private boolean postOnly = true;
    @Resource
    private SessionRegistry sessionRegistry;

    /**
     * Method setPostOnly
     * Description
     *
     * @param postOnly boolean
     */
    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    /**
     * Method attemptAuthentication
     * Description
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }


        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        sessionRegistry.registerNewSession(request.getSession().getId(),authRequest.getPrincipal());
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    /**
     * Method obtainUsername
     * Description
     *
     * @param request HttpServletRequest
     * @return String
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        Object obj = request.getParameter(USERNAME);

        return (null == obj) ? "" : obj.toString();
    }

    /**
     * Method obtainPassword
     * Description
     *
     * @param request HttpServletRequest
     * @return String
     */
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(PASSWORD);

        return (null == obj) ? "" : obj.toString();
    }

    /**
     * Method setDetails
     * Description
     *
     * @param request     HttpServletRequest
     * @param authRequest UsernamePasswordAuthenticationToken
     */
    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        super.setDetails(request, authRequest);
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
