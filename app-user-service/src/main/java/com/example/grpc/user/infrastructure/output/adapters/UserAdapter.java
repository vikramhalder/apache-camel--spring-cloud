package com.example.grpc.user.infrastructure.output.adapters;

import com.example.grpc.user.application.port.output.UserOutputPort;
import com.example.grpc.user.domain.exception.RecordNotFundException;
import com.example.grpc.user.domain.model.Pagination;
import com.example.grpc.user.domain.model.UserCreate;
import com.example.grpc.user.domain.model.UserResponse;
import com.example.grpc.user.infrastructure.output.mapper.UserMapper;
import com.example.grpc.user.infrastructure.output.persistence.entity.User;
import com.example.grpc.user.infrastructure.output.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserOutputPort {
    final UserMapper userMapper;
    final UserRepository userRepository;


    @Override
    public UserResponse details(Long id) {
        var user = userRepository.findById(id).orElseThrow(RecordNotFundException::new);
        return userMapper.toUser(user);
    }

    @Override
    public Pagination<UserResponse> filter() {
        return Pagination.page(userRepository.filter(Pageable.ofSize(10), null, null).map(userMapper::toUser));
    }

    @Override
    @Transactional
    public UserResponse save(UserCreate input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        userRepository.save(user);
        return userMapper.toUser(user);
    }
}
