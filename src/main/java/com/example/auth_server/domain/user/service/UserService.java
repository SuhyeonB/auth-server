package com.example.auth_server.domain.user.service;

import com.example.auth_server.common.jwt.JwtProvider;
import com.example.auth_server.domain.user.dto.SignupRequest;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignupRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("user already exists");

        String encoded = passwordEncoder.encode(dto.getPassword());
        User user = User.builder()
                .email(dto.getEmail())
                .password(encoded)
                .nickname(dto.getNickname())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public String login(LoginRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("invalid credentials");
        }

        return jwtProvider.generateToken(user.getId(), user.getRole());
    }
}
