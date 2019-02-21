package com.ttg.service.park.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Title: ConfigBean</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/6 16:41
 */
@Configuration
public class ConfigBean {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    /*@Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MyMetaObjectHandler();
    }*/
}
