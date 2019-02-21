package com.ttg.service.park.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: ParkApiProperties</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/10 9:58
 */
@Data
@Configuration
/*@PropertySource("classpath:config/pay-config-dev.properties")*/
@ConfigurationProperties(prefix = "park")
public class ParkProperties {

    private String appId;
    private String appSecret;

    private String paymentInfoUrl;
    private String syncOrderUrl;

}
