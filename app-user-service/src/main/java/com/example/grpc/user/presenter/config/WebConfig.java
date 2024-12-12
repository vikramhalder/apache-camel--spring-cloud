package com.example.grpc.user.presenter.config;

import com.example.grpc.user.common.mapper.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtil.objectMapper();
    }

}
