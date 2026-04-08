package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.member.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public MemberDTO selectMember(String username) {
        String sql = """
                select m.mem_id,
                       mem_name ,
                       mem_pass ,
                       mr.role_name
                  from member m
                  left outer join member_role mr
                on ( m.mem_id = mr.mem_id )
                 where m.mem_id = ?
                """;
        MemberDTO memberDTO = null;
        try (Connection conn = ConnectionFactory.createConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String role_name = rs.getString("role_name");
                    if (memberDTO == null) {
                        memberDTO = MemberDTO.builder()
                                .memId(rs.getString("mem_id"))
                                .memName(rs.getString("mem_name"))
                                .memPass(rs.getString("mem_pass"))
                                .memRoles(new ArrayList<>())
                                .build();
                    }
                    if (StringUtils.isNotBlank(role_name)) {
                        memberDTO.getMemRoles().add(role_name);
                    }
                }
                return memberDTO;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public MemberDTO selectMemberDummy(String username, String password) {
        String sql = """
                select mem_id, mem_name, mem_pass
                from member where mem_id = ? and mem_pass = ?
                """;
        try (Connection conn = ConnectionFactory.createConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return MemberDTO.builder()
                            .memId(rs.getString("mem_id"))
                            .memName(rs.getString("mem_name"))
                            .memPass(rs.getString("mem_pass"))
                            .memRoles(Collections.emptyList())
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<MemberDTO> selectAllMembers() {
        // TODO Auto-generated method stub
        return null;
    }
}
