package kr.or.ddit.auth.exception;

/**
 * 비밀번호 오류 등
 */
public class BadCredentialsException extends AuthenticationException {

    public BadCredentialsException() {
        super("비밀번호 오류");
    }

}
