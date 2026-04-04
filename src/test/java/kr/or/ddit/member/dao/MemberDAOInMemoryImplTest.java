package kr.or.ddit.member.dao;

import java.util.List;

import org.junit.jupiter.api.Test;

import kr.or.ddit.member.dto.MemberDTO;

public class MemberDAOInMemoryImplTest {
    @Test
    void testSelectAllMembers() {
        List<MemberDTO> memberDTOs = new MemberDAOInMemoryImpl().selectAllMembers();

        memberDTOs.stream().forEach(System.out::println);
    }

    @Test
    void testSelectMember() {

    }
}
