package com.example.grpc.user.application.port.input;

import com.example.grpc.user.domain.model.Pagination;
import com.example.grpc.user.domain.model.UserCreate;
import com.example.grpc.user.domain.model.UserResponse;

public interface UserInputCase {
    UserResponse getById(Long id);

    Pagination<UserResponse> filter();

    UserResponse create(UserCreate input);
}
