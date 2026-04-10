package kr.or.ddit.member.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.member.service.MemberServiceImpl;

public class MemberDAOImplTest {
    MemberDAOImpl dao = new MemberDAOImpl();
    MemberServiceImpl memberServiceImpl = new MemberServiceImpl();

    @Test
    void testSelectDummy() {
        MemberDTO memberDTO = dao.selectMemberDummy("asd", "asd ' or '1' = '1';delete from member where '1' = '1");
        assertNull(memberDTO);
    }

    @Test
    void testSqlInjection() {
        MemberDTO memberDTO = dao.selectMember("a001123123123123' or '1' = '1");
        assertNull(memberDTO);
    }

    @Test
    void testSelectMember() {
        MemberDTO memberDTO = dao.selectMember("b001");
        System.out.println(memberDTO);
        assertNotNull(memberDTO);
        memberDTO = dao.selectMember("a001123123123123");
        assertNull(memberDTO);
        System.out.println(memberDTO);

    }

    @Test
    void testSelectMemberList() {
        dao.selectMemberList().forEach(System.out::println);
    }

    @Test
    void testUpdateMember() {
        String username = "x001";
        String oldPassword = "java";
        String newPassword = "java1";

        memberServiceImpl.changePassword(username, oldPassword, newPassword);

        MemberDTO memberDTO = dao.selectMember(username);
        String updatedPass = memberDTO.getMemPass();
        assertEquals(newPassword, updatedPass);

        // 테스트를 통과하면 다시 원래대로 돌리기

        memberServiceImpl.changePassword(username, newPassword, oldPassword);

        memberDTO = dao.selectMember(username);
        updatedPass = memberDTO.getMemPass();
        assertEquals(oldPassword, updatedPass);
    }
}
