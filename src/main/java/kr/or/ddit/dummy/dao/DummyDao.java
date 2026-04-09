package kr.or.ddit.dummy.dao;

import java.util.List;

import kr.or.ddit.dummy.dto.DummyDto;

public interface DummyDao {
    int insertDummy(DummyDto dummy);

    int deleteDummy(int col1);

    DummyDto selectDummy(int col1); // 없으면 null

    List<DummyDto> selectDummyList(); // 없으면 빈 배열
}
