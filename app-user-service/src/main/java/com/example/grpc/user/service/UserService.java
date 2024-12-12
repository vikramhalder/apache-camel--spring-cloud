package com.example.grpc.user.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    public List<String> list() {
        return new ArrayList<>();
    }

    public String getById(Long id) {
        return String.valueOf(id);
    }

    public void save(String json) {
    }

    public void update(Long id, String json) {
    }
}
