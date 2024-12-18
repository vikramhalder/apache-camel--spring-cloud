package com.example.grpc.user.domain.model;

import lombok.Data;

@Data
public class UserCreate {
    private String name;
    private String email;
}
