package com.example.grpc.user.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiRoute extends RouteBuilder {

    @Value("${server.port}")
    private int serverPort;

    @Value("${server.name}")
    private String serverName;

    @Value("${server.version}")
    private String serverVersion;

    @Value("${server.servlet-context-path}")
    private String contextPath;


    @Override
    public void configure() {
        restConfiguration()
                .port(serverPort)
                .contextPath(contextPath)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", serverName)
                .apiProperty("api.version", serverVersion)
                .component("netty-http")
                .bindingMode(RestBindingMode.json);

        rest("/user")
                .get("/").to("bean:userService?method=list")
                .get("/{id}").to("bean:userService?method=getById(${header.id})")
                .post("/").consumes("application/json").type(String.class).to("bean:userService?method=save(${body})")
                .post("/{id}").consumes("application/json").type(String.class).to("bean:userService?method=update(${header.id},${body})");
    }
}