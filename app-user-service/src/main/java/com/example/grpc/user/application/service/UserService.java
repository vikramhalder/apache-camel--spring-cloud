package com.example.grpc.user.application.service;

import com.example.grpc.user.application.port.input.UserInputCase;
import com.example.grpc.user.application.port.output.UserOutputPort;
import com.example.grpc.user.domain.model.Pagination;
import com.example.grpc.user.domain.model.UserCreate;
import com.example.grpc.user.domain.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserInputCase {

    final UserOutputPort userOutputPort;


    @Override
    public UserResponse getById(Long id) {
        return userOutputPort.details(id);
    }

    @Override
    public Pagination<UserResponse> filter() {
        return userOutputPort.filter();
    }

    @Override
    public UserResponse create(UserCreate input) {
        return userOutputPort.save(input);
    }
}
