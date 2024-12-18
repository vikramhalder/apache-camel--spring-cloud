package com.example.grpc.user.infrastructure.config;

import com.example.grpc.user.application.util.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationBeans {


    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtil.objectMapper();
    }
}
