package com.baixing.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ao.wang
 *
 * @create: 2020-10-12
 **/
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        System.getProperties().setProperty("logging.file", "log/devops-user-service");
        SpringApplication.run(Application.class, args);
    }
    
}
