package com.example.auth_server.domain.user.controller;

import com.example.auth_server.domain.user.dto.CreateUserRequest;
import com.example.auth_server.domain.user.dto.LoginRequest;
import com.example.auth_server.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest dto) {

        ResponseCookie cookie = ResponseCookie
                .from("userId", String.valueOf(userService.login(dto)))
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("logged in");
    }

    @GetMapping("/me")
    public String me(@CookieValue(name = "userId", required = false) Long userId) {
        return "당신은 userId=" + userId;
    }

    @PostMapping("/session-login")
    public ResponseEntity<String> sessionLogin (
            @RequestBody LoginRequest dto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Long userId = userService.login(dto);
        session.setAttribute("LOGIN_USER_ID", userId);

        return ResponseEntity.ok().body("logged in");
    }

    @GetMapping("/session-me")
    public String sessionMe(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER_ID") == null) {
            return "로그인 안 됨";
        }
        return "userId=" + session.getAttribute("LOGIN_USER_ID");
    }
}
