package com.ttg.service.park.config;

//~--- non-JDK imports --------------------------------------------------------


//~--- classes ----------------------------------------------------------------


import com.google.common.collect.Lists;
import com.ttg.service.park.intelligent.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

/**
 * Class SecurityConfig
 * Description
 * Create 2017-03-10 17:28:29
 *
 * @author Ardy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Field userDetailService
     * Description
     *
     * @return 返回值说明：
     */
    @Bean
    public UserDetailService userDetailService() {
        return new UserDetailService();
    }


    /**
     * Method configure
     * Description 说明：.successHandler(
     * authenticationSuccessHandler()).failureHandler(
     * authenticationFailureHandler())
     *
     * @param http 说明：
     * @throws Exception 异常：
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers(
                "/css/**", "/js/**", "/img/**", "/i/**", "/fonts/**", "/favicon.ico*", "/Public/**","/element/**",
                "/user/css/**","/user/js/**","/user/images/**","/user/layer_mobile/**","/admin/css/**","/admin/js/**","/admin/images/**",
                "/merchant/auth/**", "/layui/**",
                //"/merchant/category/**","/merchant/template/**","/merchant/file/**","/merchant/setting/**","/merchant/util/**",
                "/test/**","/test/hello/*","/MP_verify_6NrS37AjvtvZlnKW.txt",
                "/user/order/**","/user/pay/**","/pay/order/**","/merchant/car/successInfo/**","/sys/util/showimg","/merchant/Advertisement/enList/**","/merchant/setting/info",
                //"/merchant/car/**","/merchant/Advertisement/**","/merchant/Advertisement/category/**","/merchant/setting/**","/sys/util/**",
                "/swagger-ui.html","/swagger/**","/v2/api-docs","/swagger-resources/**","/webjars/springfox-swagger-ui/**")
                .permitAll().anyRequest().hasAnyRole("role") //任意路径登录后可以访问
                .and().formLogin().loginPage("/merchant/auth/login").permitAll()
                .and().exceptionHandling().accessDeniedPage("/merchant/auth/denied")
                .and().logout().invalidateHttpSession(true)
                .and().sessionManagement().sessionAuthenticationStrategy(sas()).sessionFixation().migrateSession();
        http.addFilterAt(concurrencyFilter(), ConcurrentSessionFilter.class);
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    /**
     * Method authenticationFailureHandler
     * Description 说明：
     *
     * @return 返回值说明：
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    /**
     * Method logoutSuccessHandler
     * Description 说明：
     *
     * @return 返回值说明：
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    /**
     * Method configureGlobal
     * Description 说明：
     *
     * @param auth 说明：
     * @throws Exception 异常：
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        // 记住用户
        auth.eraseCredentials(true);
    }

    /**
     * Method md5PasswordEncoder
     * Description 说明：
     *
     * @return 返回值说明：
     */
    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

        md5PasswordEncoder.setEncodeHashAsBase64(true);

        return md5PasswordEncoder;
    }

    /**
     * Method authenticationFilter
     * Description 说明：
     *
     * @return 返回值说明：
     * @throws Exception 异常：
     */
    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/merchant/userLogin");
        authenticationFilter.setUsernameParameter("username");
        authenticationFilter.setPasswordParameter("password");
        authenticationFilter.setPostOnly(true);
        authenticationFilter.setAuthenticationManager(this.authenticationManager());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setSessionAuthenticationStrategy(sas());
        return authenticationFilter;
    }

    /**
     * Method loginSuccessHandler
     * Description 说明：
     *
     * @return 返回值说明：
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    /**
     * Method reflectionSaltSource
     * Description 说明：密码盐值
     *
     * @return 返回值说明：
     */
    @Bean
    public ReflectionSaltSource reflectionSaltSource() {
        ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();

        reflectionSaltSource.setUserPropertyToUse("username");

        return reflectionSaltSource;
    }

    /**
     * Method authenticationProvider
     * Description 说明：配置用户信息
     *
     * @return 返回值说明：
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(md5PasswordEncoder());
        authenticationProvider.setSaltSource(reflectionSaltSource());
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;
    }

    //SpringSecurity内置的session监听器
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Bean
    public ConcurrentSessionFilter concurrencyFilter() {
        return new ConcurrentSessionFilter(sessionRegistry(),sessionInformationExpiredStrategy());
    }


    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new SimpleRedirectSessionInformationExpiredStrategy("/merchant/auth/expire");
    }

    @Bean
    public CompositeSessionAuthenticationStrategy sas() {
        return new CompositeSessionAuthenticationStrategy(Lists.newArrayList(concurrentSessionControlAuthenticationStrategy(), sessionFixationProtectionStrategy(), registerSessionAuthenticationStrategy()));
    }

    @Bean
    public SessionFixationProtectionStrategy sessionFixationProtectionStrategy() {
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy =  new SessionFixationProtectionStrategy();
/*        sessionFixationProtectionStrategy.setAlwaysCreateSession(true);*/
        sessionFixationProtectionStrategy.setMigrateSessionAttributes(true);
        return sessionFixationProtectionStrategy;
    }

    @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        //concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
        //concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);
        return concurrentSessionControlAuthenticationStrategy;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
