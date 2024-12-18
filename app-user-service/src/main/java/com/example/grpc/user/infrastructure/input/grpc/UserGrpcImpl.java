package com.example.grpc.user.infrastructure.input.grpc;

import com.example.grpc.user.application.port.input.UserInputCase;
import com.example.grpc.user.domain.exception.RecordNotFundException;
import com.example.grpc.user.domain.model.Pagination;
import com.example.grpc.user.domain.model.UserResponse;
import com.example.grpc.user.infrastructure.output.mapper.UserMapper;
import com.example.user.gen.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserGrpcImpl extends UserServiceProtoRouteGrpc.UserServiceProtoRouteImplBase {

    final UserMapper userMapper;
    final UserInputCase userInputCase;

    public void userGetById(GrpcUserIdRequest request, StreamObserver<GrpcUserResponse> responseObserver) throws RecordNotFundException {
        final UserResponse user = userInputCase.getById(request.getId());
        responseObserver.onNext(userMapper.toGrpcUserResponse(user));
        responseObserver.onCompleted();
    }

    public void userSave(GrpcUserCreateRequest request, StreamObserver<GrpcUserResponse> responseObserver) {
        final UserResponse user = userInputCase.create(userMapper.toUserCreate(request));
        responseObserver.onNext(userMapper.toGrpcUserResponse(user));
        responseObserver.onCompleted();
    }

    @Override
    public void userGetAll(GrpcUserFilterRequest request, StreamObserver<GrpcItemUserResponse> responseObserver) {
        final Pagination<UserResponse> pagination = userInputCase.filter();
        responseObserver.onNext(userMapper.toGrpcItemUserResponse(pagination.getData()));
        responseObserver.onCompleted();
    }
}