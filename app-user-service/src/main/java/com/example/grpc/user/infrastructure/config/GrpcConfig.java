package com.example.grpc.user.infrastructure.config;

import com.example.grpc.user.infrastructure.input.grpc.UserGrpcImpl;
import com.example.user.gen.grpc.UserServiceProtoRouteGrpc;
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
public class GrpcConfig {
    @Bean
    public GrpcComponent grpcComponent() {
        return new GrpcComponent();
    }

    @Bean
    public Server serverAuto(@Value("${camel.grpc-port}") int grpcServerPort, final UserGrpcImpl userGrpc) throws IOException {
        log.info("gRpc server staring port: {}", grpcServerPort);
        return ServerBuilder.forPort(grpcServerPort).addService(userGrpc).build().start();
    }

    @RequiredArgsConstructor
    static class serverManual extends RouteBuilder {
        final UserGrpcImpl userServiceProtoRouteGrpc;

        @Override
        public void configure() {
            from("grpc://0.0.0.0:{{camel.grpc-port}}/".concat(UserServiceProtoRouteGrpc.SERVICE_NAME))
                    .log("Received request: ${body}")
                    .bean(userServiceProtoRouteGrpc)
            ;
        }
    }
}
