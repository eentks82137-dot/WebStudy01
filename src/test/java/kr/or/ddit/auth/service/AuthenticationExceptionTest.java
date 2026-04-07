package kr.or.ddit.auth.service;

import org.junit.jupiter.api.Test;

import kr.or.ddit.auth.exception.BadCredentialsException;
import kr.or.ddit.auth.exception.UsernameNotFoundException;

public class AuthenticationExceptionTest {
    @Test
    public void printUsernameNotFoundException() {
        try {
            throw new UsernameNotFoundException("test");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
    }

    @Test
    public void printBadCredentialsException() {
        try {
            throw new BadCredentialsException();
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
    }
}
