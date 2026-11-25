package com.example.auth_server.common.jwt;

import com.example.auth_server.domain.user.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    private JwtProvider createProvider(String secret, long exp) {
        JwtProvider provider = new JwtProvider();
        ReflectionTestUtils.setField(provider, "secret", secret);
        ReflectionTestUtils.setField(provider, "TOKEN_EXP", exp);
        provider.init();
        return provider;
    }

    @BeforeEach
    void setup() {
        jwtProvider = createProvider("test-secret-key-must-be-at-least-32-bytes!!", 1_800_000L);
    }

    @Test
    @DisplayName("발급 직후 토큰은 검증에 성공하고 sub에 userId가 담긴다")
    public void validToken_returnsTrue() {
        String token = jwtProvider.generateToken(1L, Role.USER);

        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    @DisplayName("payload를 변조한 토큰은 검증에 실패한다")
    public void tamperedToken_returnsFalse() {
        String token = jwtProvider.generateToken(1L, Role.USER);

        String tamperedPayload = Base64.getUrlDecoder().decode(token.split("\\.")[1]).toString().replace("1L", "2L");
        String tamperedToken = String.join(".", token.split("\\.")[0], tamperedPayload, token.split("\\.")[2]);

        assertFalse(jwtProvider.validateToken(tamperedToken));
    }

    @Test
    @DisplayName("만료된 토큰은 검증에 실패한다")
    public void expiredToken_returnsFalse() {
        jwtProvider = createProvider("test-secret-key-must-be-at-least-32-bytes!!", -1000L);

        String expiredToken = jwtProvider.generateToken(1L, Role.USER);

        assertFalse(jwtProvider.validateToken(expiredToken));
    }

    @Test
    @DisplayName("다른 키로 서명된 토큰은 검증에 실패한다")
    public void tokenSignedWithDifferentKey_returnsFalse() {
        JwtProvider otherJwtProvider = createProvider("this-secret-key-is-not-what-you-expect==", 1_800_000L);

        String token = otherJwtProvider.generateToken(1L, Role.USER);

        assertFalse(jwtProvider.validateToken(token));
    }

    @Test
    @DisplayName("형식이 잘못된 토큰은 검증에 실패한다")
    public void malformedToken_returnsFalse() {
        String token = "abcd";

        assertFalse(jwtProvider.validateToken(token));
    }
}