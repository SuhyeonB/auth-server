package com.example.auth_server.domain.user.service;

import com.example.auth_server.domain.user.dto.CreateUserRequest;
import com.example.auth_server.domain.user.dto.LoginRequest;
import com.example.auth_server.domain.user.entity.User;
import com.example.auth_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(CreateUserRequest dto) {

        String encoded = passwordEncoder.encode(dto.getPassword());
        User user = User.builder()
                .email(dto.getEmail())
                .password(encoded)
                .nickname(dto.getNickname())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public Long login(LoginRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + dto.getEmail()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("passwords do not match");
        }

        return user.getId();
    }
}
