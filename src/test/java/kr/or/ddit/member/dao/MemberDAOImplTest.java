package kr.or.ddit.member.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import kr.or.ddit.member.dto.MemberDTO;

public class MemberDAOImplTest {
    MemberDAOImpl dao = new MemberDAOImpl();

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
    void testUpdatePassword() {
        MemberDTO memberDTO = MemberDTO.builder().memId("x001").memPass("java").build();
        dao.updateMemberPassword(memberDTO);
    }

}
