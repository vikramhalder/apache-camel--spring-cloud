package com.example.grpc.user.presenter.grpc;

import com.example.grpc.user.service.UserService;
import com.example.user.gen.grpc.UserServiceProto;
import com.example.user.gen.grpc.UserServiceProtoRouteGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoute extends UserServiceProtoRouteGrpc.UserServiceProtoRouteImplBase {

    final UserService userService;

    public void userGetById(UserServiceProto.UserIdRequest request, StreamObserver<UserServiceProto.UserResponse> responseObserver) {
        log.info("userGetById");
    }

    public void userSave(UserServiceProto.UserCreateRequest request, StreamObserver<UserServiceProto.UserResponse> responseObserver) {
        log.info("userSave");
    }

    public void userGetAll(UserServiceProto.UserFilterRequest request, StreamObserver<UserServiceProto.ItemUserResponse> responseObserver) {
        log.info("userGetAll");
    }
}