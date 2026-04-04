package kr.or.ddit.admin.service;

import java.util.List;

import kr.or.ddit.member.dao.MemberDAOInMemoryImpl;
import kr.or.ddit.member.dto.MemberDTO;

public class AdminMemberService {

    public List<MemberDTO> getAllMembers() {
        return new MemberDAOInMemoryImpl().selectAllMembers();
    }

}
