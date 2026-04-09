package kr.or.ddit.dummy.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.ddit.db.template.DummyTemplateCase1;
import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase2 extends DummyTemplateCase1<DummyDto> {

    @Override
    protected String step1() {
        return """
                select col1, col2 from dummy
                """;
    }

    @Override
    protected void step4(PreparedStatement pstmt) throws SQLException {
    }

    @Override
    protected DummyDto step6(ResultSet rs) throws SQLException {
        DummyDto dto = new DummyDto();
        dto.setCol1(rs.getInt("col1"));
        dto.setCol2(rs.getString("col2"));
        return dto;
    }
}
