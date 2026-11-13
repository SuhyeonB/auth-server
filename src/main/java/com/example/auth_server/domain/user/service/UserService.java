package com.example.auth_server.domain.user.service;

import com.example.auth_server.domain.user.dto.CreateUserRequest;
import com.example.auth_server.domain.user.entity.User;
import com.example.auth_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(CreateUserRequest dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .build();

        userRepository.save(user);
    }
}
