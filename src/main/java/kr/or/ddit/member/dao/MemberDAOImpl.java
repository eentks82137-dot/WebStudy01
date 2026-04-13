package kr.or.ddit.member.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.Failable;

import kr.or.ddit.db.template.JdbcTemplate;
import kr.or.ddit.member.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private Function<ResultSet, MemberDTO> rowMapper = Failable.asFunction(rs -> {
        String role_name = rs.getString("role_name");
        MemberDTO memberDTO = MemberDTO.builder()
                .memId(rs.getString("mem_id"))
                .memName(rs.getString("mem_name"))
                .memPass(rs.getString("mem_pass"))
                .memRoles(new ArrayList<>())
                .build();
        if (StringUtils.isNotBlank(role_name)) {
            memberDTO.getMemRoles().add(role_name);
        }
        return memberDTO;
    });

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
        List<MemberDTO> list = jdbcTemplate.queryForList(sql, Failable.asConsumer(pstmt -> {
            pstmt.setString(1, username);
        }), rowMapper);

        if (list.isEmpty()) {
            return null;
        } else {
            // 하나의 member dto를 만들고 그 안에 role name들을 병합시키기
            List<String> memRoles = list.stream().flatMap(mDto -> mDto.getMemRoles().stream()).toList();
            MemberDTO one = list.getFirst();
            one.setMemRoles(memRoles);
            return one;
        }
    }

    public MemberDTO selectMemberDummy(String username, String password) {
        String sql = """
                select mem_id, mem_name, mem_pass
                from member where mem_id = ? and mem_pass = ?
                """;
        return jdbcTemplate.queryForObject(sql, Failable.asConsumer(pstmt -> {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
        }), Failable.asFunction(rs -> {
            return MemberDTO.builder()
                    .memId(rs.getString("mem_id"))
                    .memName(rs.getString("mem_name"))
                    .memPass(rs.getString("mem_pass"))
                    .memRoles(Collections.emptyList())
                    .build();
        }));
    }

    @Override
    public List<MemberDTO> selectMemberList() {
        String sql = """
                select m.mem_id,
                       mem_name ,
                       mem_pass ,
                       mr.role_name
                  from member m
                  left outer join member_role mr
                on ( m.mem_id = mr.mem_id )
                """;
        return jdbcTemplate.queryForList(sql, null, rowMapper);
    }

    @Override
    public void updateMemberPassword(MemberDTO memberDTO) {
        String sql = """
                update member
                set mem_pass = ?
                where mem_id = ?
                """;
        jdbcTemplate.update(sql, Failable.asConsumer(pstmt -> {
            pstmt.setString(1, memberDTO.getMemPass());
            pstmt.setString(2, memberDTO.getMemId());
        }));
    }

    @Override
    public int updatePassword(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }
}
