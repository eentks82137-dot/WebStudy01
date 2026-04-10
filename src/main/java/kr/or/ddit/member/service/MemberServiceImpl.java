package kr.or.ddit.member.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.auth.exception.AuthenticationException;
import kr.or.ddit.auth.service.AuthenticateService;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.dto.MemberDTO;

public class MemberServiceImpl implements MemberService {
    private MemberDAO dao = new MemberDAOImpl();
    private AuthenticateService authenticateService = new AuthenticateService();

    @Override
    public List<MemberDTO> readMemberList() {
        return dao.selectMemberList();
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) throws AuthenticationException {
        MemberDTO memberDTO = authenticateService.authenticate(username, oldPassword);

        // 비밀번호 수정
        // TODO (추후 암호화 해야함)
        memberDTO.setMemPass(newPassword);
        dao.updateMemberPassword(memberDTO);
    }
}
