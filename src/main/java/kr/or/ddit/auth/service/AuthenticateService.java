package kr.or.ddit.auth.service;

import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOInMemoryImpl;
import kr.or.ddit.member.dto.MemberDTO;

/**
 * 사용자의 신원 확인(인증)을 담당할 Business Logic Layer
 */

public class AuthenticateService {
    private MemberDAO memberDAO = new MemberDAOInMemoryImpl();

    /**
     * 사용자의 아이디와 비밀번호를 이용하여 사용자를 인증하는 메서드
     * 
     * @param username 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 인증된 사용자 정보(MemberDTO), 인증 실패 시 null
     *         (TODO 추후 Custom Exception으로 변경 예정)
     */
    public MemberDTO authenticate(String username, String password) {
        MemberDTO member = memberDAO.selectMember(username);
        if (member == null) {
            return null;
        }

        if (password.equals(member.getMemPass())) {
            return member;
        } else {
            return null;
        }
    }
}
