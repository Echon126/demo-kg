//package com.example.demo.config;
//
//import io.minio.MinioClient;
//
//import io.minio.errors.InvalidEndpointException;
//import io.minio.errors.InvalidPortException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableConfigurationProperties(MinioProp.class)
//public class MinioConfiguration {
//    @Autowired
//    private MinioProp minioProp;
//
//
//    @Bean
//    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
//        MinioClient client = new MinioClient(minioProp.getEndpoint(), minioProp.getAccesskey(), minioProp.getSecretKey());
//        return client;
//    }
//
//}