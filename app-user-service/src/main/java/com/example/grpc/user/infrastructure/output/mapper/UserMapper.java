package com.example.grpc.user.infrastructure.output.mapper;

import com.example.grpc.user.domain.model.UserCreate;
import com.example.grpc.user.domain.model.UserResponse;
import com.example.grpc.user.infrastructure.output.persistence.entity.User;
import com.example.user.gen.grpc.GrpcItemUserResponse;
import com.example.user.gen.grpc.GrpcUserCreateRequest;
import com.example.user.gen.grpc.GrpcUserResponse;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    UserResponse toUser(User user);

    UserCreate toUserCreate(GrpcUserCreateRequest user);

    GrpcUserResponse toGrpcUserResponse(UserResponse user);


    default GrpcItemUserResponse toGrpcItemUserResponse(List<UserResponse> users) {
        if (users == null) {
            users = new ArrayList<>();
        }
        return GrpcItemUserResponse.newBuilder().addAllUsers(users.stream().map(this::toGrpcUserResponse).toList()).build();
    }
}
