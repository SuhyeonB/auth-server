package com.example.auth_server.domain.user.controller;

import com.example.auth_server.domain.user.dto.SignupRequest;
import com.example.auth_server.domain.user.dto.LoginRequest;
import com.example.auth_server.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createUser (@Valid @RequestBody SignupRequest dto) {
        userService.signup(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}
