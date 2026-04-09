package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.auth.exception.AuthenticationException;
import kr.or.ddit.member.dto.MemberDTO;

public interface MemberService {
    /**
     * 모든 사용자 정보를 조회하는 메서드
     * 
     * @return 사용자 정보 리스트
     */
    public List<MemberDTO> readMemberList();

    /**
     * 비밀번호 변경
     * 
     * @param username    사용자 이름
     * @param oldPassword 이전 비밀번호
     * @param newPassword 새로운 비밀번호
     * @throws AuthenticationException 인증 실패 시 발생하는 예외
     */
    public void changePassword(String username, String oldPassword, String newPassword) throws AuthenticationException;
}
