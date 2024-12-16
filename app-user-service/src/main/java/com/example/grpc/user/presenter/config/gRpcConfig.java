package com.example.grpc.user.presenter.config;

import com.example.grpc.user.common.mapper.JacksonUtil;
import com.example.grpc.user.presenter.grpc.UserServiceProtoImpl;
import com.example.user.gen.grpc.UserServiceProtoRouteGrpc;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.grpc.GrpcComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class gRpcConfig {
    @Value("${camel.grpc-port}")
    private int grpcServerPort;

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtil.objectMapper();
    }

    @Bean
    public GrpcComponent grpcComponent() {
        return new GrpcComponent();
    }

    @Bean
    public Server serverAuto(final UserServiceProtoImpl userServiceProtoRouteGrpc) throws IOException {
        log.info("gRpc server staring port: {}", grpcServerPort);
        final Server server = ServerBuilder.forPort(grpcServerPort).addService(userServiceProtoRouteGrpc).build().start();
        return server;
    }

    @RequiredArgsConstructor
    static class serverManual extends RouteBuilder {
        final UserServiceProtoImpl userServiceProtoRouteGrpc;

        @Override
        public void configure() {
            from("grpc://0.0.0.0:{{camel.grpc-port}}/".concat(UserServiceProtoRouteGrpc.SERVICE_NAME))
                    .log("Received request: ${body}")
                    .bean(userServiceProtoRouteGrpc);
        }
    }
}
