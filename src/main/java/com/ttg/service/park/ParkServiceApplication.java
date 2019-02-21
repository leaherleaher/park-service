package com.ttg.service.park;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.ttg.service.park.intelligent.mapper")
public class ParkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkServiceApplication.class, args);
    }
}
