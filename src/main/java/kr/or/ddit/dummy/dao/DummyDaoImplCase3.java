package kr.or.ddit.dummy.dao;

import java.util.List;

import org.apache.commons.lang3.function.Failable;

import kr.or.ddit.db.template.JdbcTemplate;
import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase3 implements DummyDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public int insertDummy(DummyDto dummy) {
        String sql = """
                insert into dummy
                (col1, col2)
                values (?, ?)
                """;
        return jdbcTemplate.update(sql, Failable.asConsumer(pstmt -> {
            pstmt.setInt(1, dummy.getCol1());
            pstmt.setString(2, dummy.getCol2());
        }));
    }

    @Override
    public DummyDto selectDummy(int col1) {
        String sql = """
                select col1, col2
                from dummy
                where col1 = ?
                """;
        return jdbcTemplate.queryForObject(sql, Failable.asConsumer(pstmt -> {
            pstmt.setInt(1, col1);
        }), Failable.asFunction(rs -> {
            DummyDto dto = new DummyDto();
            dto.setCol1(rs.getInt("col1"));
            dto.setCol2(rs.getString("col2"));
            return dto;
        }));
    }

    @Override
    public List<DummyDto> selectDummyList() {
        String sql = """
                select col1, col2
                from dummy
                """;
        return jdbcTemplate.queryForList(sql, pstmt -> {
        }, Failable.asFunction(rs -> {
            DummyDto dto = new DummyDto();
            dto.setCol1(rs.getInt("col1"));
            dto.setCol2(rs.getString("col2"));
            return dto;
        }));
    }

    @Override
    public int deleteDummy(int col1) {
        String sql = """
                delete dummy where col1 = ?
                """;
        return jdbcTemplate.update(sql, Failable.asConsumer(pstmt -> {
            pstmt.setInt(1, col1);
        }));
    }
}
