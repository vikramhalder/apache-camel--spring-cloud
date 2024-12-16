package com.example.grpc.user.presenter.grpc;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcRoute extends RouteBuilder {
    final UserRoute userRoute;

    @Override
    public void configure() {
        from("grpc://0.0.0.0:{{camel.grpc-port}}/com.example.user.gen.grpc.UserServiceProtoRoute")
                .log("Received request: ${body}")
                .bean(userRoute);
    }
}