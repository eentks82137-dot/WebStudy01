package kr.or.ddit.auth.exception;

public class SecuredResourceNotFoundException extends RuntimeException {

    public SecuredResourceNotFoundException() {
        super("보호 자원 설정 파일 위치 오류");
    }

}
