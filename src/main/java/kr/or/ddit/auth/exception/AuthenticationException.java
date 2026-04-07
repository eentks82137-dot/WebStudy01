package kr.or.ddit.auth.exception;

/**
 * 인증 과정에서 발생하는 예외를 나타내는 커스텀 런타임 예외 클래스
 * UsernameNotFoundException, BadCredentialsException 등으로 세분화 가능
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
