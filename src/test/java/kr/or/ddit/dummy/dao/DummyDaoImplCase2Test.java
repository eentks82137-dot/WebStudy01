package kr.or.ddit.dummy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase2Test {
    DummyDaoImplCase2 dao = new DummyDaoImplCase2();

    @Test
    void testQueryTemplate() {
        List<DummyDto> dto = dao.queryTemplate();
        assertEquals(true, dto.isEmpty());

    }
}
