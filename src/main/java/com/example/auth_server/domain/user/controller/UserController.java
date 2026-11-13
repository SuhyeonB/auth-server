package com.example.auth_server.domain.user.controller;

import com.example.auth_server.domain.user.dto.CreateUserRequest;
import com.example.auth_server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser (@RequestBody CreateUserRequest dto) {
        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }
}
