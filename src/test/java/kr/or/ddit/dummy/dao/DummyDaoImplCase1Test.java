package kr.or.ddit.dummy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase1Test {
    private final DummyDao dao = new DummyDaoImplCase1();

    private void truncateDummy() {
        String sql = """
                truncate table dummy
                """;
        try (Connection conn = ConnectionFactory.createConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @BeforeEach
    void setUp() {
        int col1 = 1;
        DummyDto dto = new DummyDto(col1, "test1");
        dao.insertDummy(dto);
    }

    @AfterAll
    void tearDown() {
        truncateDummy();
    }

    @Test
    void testInsertDummy() {
        int col1 = 2;
        DummyDto dto = new DummyDto(col1, "test1");
        int result = dao.insertDummy(dto);
        assertEquals(1, result);
    }

    @Test
    void testSelectDummy() {
        int col1 = 1;
        DummyDto dto = dao.selectDummy(col1);
        assertEquals("test1", dto.getCol2());

    }

    @Test
    void testSelectDummyList() {
        List<DummyDto> dtos = dao.selectDummyList();
        assertEquals(false, dtos.isEmpty());
    }
}
