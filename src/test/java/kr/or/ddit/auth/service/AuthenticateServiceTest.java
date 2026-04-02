package kr.or.ddit.auth.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class AuthenticateServiceTest {

    AuthenticateService authenticateService = new AuthenticateService();

    /**
     * 존재하지 않는 사용자 아이디로 인증 시도 -> null 반환 예상
     */
    @Test
    void testAuthenticate() {
        assertNull(authenticateService.authenticate("c001", "1234"));
    }
}
