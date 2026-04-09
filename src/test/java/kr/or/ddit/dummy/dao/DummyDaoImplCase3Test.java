package kr.or.ddit.dummy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase3Test {
    DummyDao dao = new DummyDaoImplCase3();

    @BeforeEach
    void setUp() {
        DummyDto dto = new DummyDto(1, "test1");
        dao.insertDummy(dto);
    }

    @AfterEach
    void tearDown() {
        dao.deleteDummy(1);
    }

    @Test
    void testSelectDummy() {
        DummyDto dto = dao.selectDummy(1);
        System.out.println(dto);
        assertEquals("test1", dto.getCol2());
    }

    @Test
    void testSelectDummyList() {
        System.out.println(dao.selectDummyList());
        assertEquals(1, dao.selectDummyList().size());
    }
}
