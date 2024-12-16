package com.example.grpc.user.presenter.grpc;

import com.example.grpc.user.common.mapper.UserMapper;
import com.example.grpc.user.data.entity.User;
import com.example.grpc.user.service.UserService;
import com.example.user.gen.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceProtoImpl extends UserServiceProtoRouteGrpc.UserServiceProtoRouteImplBase {

    final UserMapper userMapper;
    final UserService userService;

    public void userGetById(UserIdRequest request, StreamObserver<UserResponse> responseObserver) {
        final User user = userService.getById(request.getId());
        responseObserver.onNext(userMapper.mapToUserResponse(user));
        responseObserver.onCompleted();
    }

    public void userSave(UserCreateRequest request, StreamObserver<UserResponse> responseObserver) {
        final User user = userService.saveOrUpdate(null, request.getName(), request.getEmail());
        responseObserver.onNext(userMapper.mapToUserResponse(user));
        responseObserver.onCompleted();
    }

    @Override
    public void userGetAll(UserFilterRequest request, StreamObserver<ItemUserResponse> responseObserver) {
        final List<User> list = userService.list(request.getName(), request.getEmail());
        responseObserver.onNext(userMapper.mapToUserResponseList(list));
        responseObserver.onCompleted();
    }
}