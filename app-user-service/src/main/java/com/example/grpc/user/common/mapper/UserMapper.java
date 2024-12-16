package com.example.grpc.user.common.mapper;

import com.example.grpc.user.data.entity.User;
import com.example.user.gen.grpc.ItemUserResponse;
import com.example.user.gen.grpc.UserResponse;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    UserResponse mapToUserResponse(User user);

    default ItemUserResponse mapToUserResponseList(List<User> users) {
        if (users == null) {
            users = new ArrayList<>();
        }
        return ItemUserResponse.newBuilder().addAllUsers(users.stream().map(this::mapToUserResponse).toList()).build();
    }
}
