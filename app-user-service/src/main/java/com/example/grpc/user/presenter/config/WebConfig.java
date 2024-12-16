package com.example.grpc.user.presenter.config;

import com.example.grpc.user.common.mapper.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.component.grpc.GrpcComponent;
import org.apache.camel.component.grpc.GrpcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtil.objectMapper();
    }

    @Bean
    public GrpcComponent grpcComponent() {
        return new GrpcComponent();
    }

    @Bean
    public GrpcConfiguration grpcConfiguration(@Value("${camel.grpc-port}") int port) {
        GrpcConfiguration configuration = new GrpcConfiguration();
        configuration.setService("user-service");
        configuration.setHost("0.0.0.0");
        configuration.setPort(port);
        return configuration;
    }

}
