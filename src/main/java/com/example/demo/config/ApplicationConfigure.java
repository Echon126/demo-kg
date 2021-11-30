package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "applicationconfig")
public class ApplicationConfigure {


    private String userName;

    private String password;

    private String jfrogUrl;

    private String type;


}
