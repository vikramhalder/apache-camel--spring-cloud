package com.example.grpc.user.service;

import com.example.grpc.user.data.entity.User;
import com.example.grpc.user.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public List<User> list(final String name, final String email) {
        try {
            return userRepository.filter(Pageable.ofSize(Integer.MAX_VALUE), name, email).toList();
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }

    public User getById(final long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public User saveOrUpdate(Long id, String name, String email) {
        User user = (id != null ? userRepository.findById(id).orElse(new User()) : new User());
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

}
