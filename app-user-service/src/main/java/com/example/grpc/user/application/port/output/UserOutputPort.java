package com.example.grpc.user.application.port.output;

import com.example.grpc.user.domain.model.Pagination;
import com.example.grpc.user.domain.model.UserCreate;
import com.example.grpc.user.domain.model.UserResponse;

public interface UserOutputPort {
    UserResponse details(Long id);

    Pagination<UserResponse> filter();

    UserResponse save(UserCreate input);

}
