package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.member.dto.MemberDTO;

/**
 * 사용자 관리와 인증에 사용할 Persistency Layer
 */

public interface MemberDAO {
    /**
     * 사용자 아이디를 이용하여 사용자 정보를 조회하는 메서드
     * 
     * @param username
     * @return
     */
    MemberDTO selectMember(String username);

    List<MemberDTO> selectMemberList();

    void updateMemberPassword(MemberDTO memberDTO);

    int updatePassword(String username, String password);
}
