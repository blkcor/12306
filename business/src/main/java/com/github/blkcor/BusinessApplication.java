package com.github.blkcor;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(scanBasePackages = "com.github.blkcor")
//@MapperScan("com.github.blkcor.mapper")
public class BusinessApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        ConfigurableEnvironment environment = app.run(args).getEnvironment();
        LOG.info("启动成功!");
        LOG.info("地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
