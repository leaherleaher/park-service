package com.ttg.service.park.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: PayProperties</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 11:20
 */
@Data
@Configuration
/*@PropertySource("classpath:config/pay-config-dev.properties")*/
@ConfigurationProperties(prefix = "pay")
public class PayProperties {
    private String uploadPath;

    private String fileServer;

    private String appDomain;

    private String authBankUrl;

    private String authMerchantUrl;

    private String bankDefaultPassword;
    private String merchantDefaultPassword;

    private String authBankPrefix;

    private String authMerchantPrefix;

    private  String payDomain;

    private String callbackUrl;

    private String userOrderUrl;

    private Boolean proxyFlag;

    private String proxyHost;

    private Integer proxyPort;
}
