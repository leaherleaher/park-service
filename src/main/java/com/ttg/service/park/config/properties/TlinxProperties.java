package com.ttg.service.park.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: TlinxProperties</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:20
 */
@Data
@Configuration
//@PropertySource("classpath:config/pay-config-dev.properties")
@ConfigurationProperties(prefix="tlinx")
public class TlinxProperties {

    private String appId;
    private String devId;
    private String appSecret;

    private String apiDomainUrl;
    private String apiTokenUrl;
    private String appTokenUrl;
    private String shopUrl;
    private String userUrl;
    private String viewMerchantUrl;
    private String apiBusiUrl;
    private String apiBusiPayListUrl;
    private String apiBusiPayOrderUrl;
    private String apiBusiPayStatusUrl;

    //tlinx2.0
    private String domainorgUrl;
    private String merchantlistUrl;
    private String langUrl;
    private String privateKey;
    private String publicKey;


}