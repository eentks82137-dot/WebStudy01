package kr.or.ddit.member.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.member.dto.MemberDTO;

public class MemberDAOInMemoryImpl implements MemberDAO {
    private final Map<String, MemberDTO> memberTable;
    {
        // DB 대신 사용할 메모리 기반의 데이터 저장소 초기화
        memberTable = new LinkedHashMap<>();
        memberTable.put("a001", MemberDTO.builder().memId("a001").memPass("java").memName("춘식이")
                .memRoles(List.of("ROLE_ADMIN", "ROLE_USER")).build());
        memberTable.put("b001", MemberDTO.builder().memId("b001").memPass("java").memName("대식이")
                .memRoles(List.of("ROLE_USER")).build());
        memberTable.put("c001", MemberDTO.builder().memId("c001").memPass("java").memName("춘삼이")
                .memRoles(List.of("ROLE_USER")).build());
        memberTable.put("d001", MemberDTO.builder().memId("d001").memPass("java").memName("영식이")
                .memRoles(List.of("ROLE_USER")).build());

    }

    @Override
    public MemberDTO selectMember(String username) {
        return memberTable.get(username);
    }

    @Override
    public List<MemberDTO> selectMemberList() {
        return memberTable.values().stream().toList();
    }
}
