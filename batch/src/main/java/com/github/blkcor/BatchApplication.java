package com.github.blkcor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class BatchApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BatchApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BatchApplication.class);
        ConfigurableEnvironment environment = app.run(args).getEnvironment();
        LOG.info("启动成功!");
        LOG.info("地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));

    }
}
