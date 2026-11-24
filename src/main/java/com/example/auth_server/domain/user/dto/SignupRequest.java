package com.example.auth_server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
}
