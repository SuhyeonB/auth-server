package com.example.auth_server.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {

    private String email;
    private String password;
    private String nickname;
}
