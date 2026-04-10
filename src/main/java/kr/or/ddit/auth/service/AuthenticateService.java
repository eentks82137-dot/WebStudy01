package kr.or.ddit.auth.service;

import kr.or.ddit.auth.exception.AuthenticationException;
import kr.or.ddit.auth.exception.BadCredentialsException;
import kr.or.ddit.auth.exception.UsernameNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.dto.MemberDTO;

/**
 * 사용자의 신원 확인(인증)을 담당할 Business Logic Layer
 */

public class AuthenticateService {
    private MemberDAO memberDAO = new MemberDAOImpl();

    /**
     * 사용자의 아이디와 비밀번호를 이용하여 사용자를 인증하는 메서드
     * 
     * @param username 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 인증된 사용자 정보(MemberDTO)
     * @throws AuthenticationException 인증 실패 시 발생
     */
    public MemberDTO authenticate(String username, String password) throws AuthenticationException {
        MemberDTO member = memberDAO.selectMember(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        if (password != null && password.equals(member.getMemPass())) {
            return member;
        } else {
            throw new BadCredentialsException();
        }
    }
}
