package kr.or.ddit.auth.exception;

/**
 * 사용자를 찾을 수 없는 경우 등
 */
public class UsernameNotFoundException extends AuthenticationException {

    public UsernameNotFoundException(String username) {
        super("%s 아이디로 등록된 사용자 없음".formatted(username));
    }

}
