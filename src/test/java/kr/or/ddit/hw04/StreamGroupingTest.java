package kr.or.ddit.hw04;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.domain.UnitType;

public class StreamGroupingTest {

    @Test
    void streamGroupingTest() {
        Map<UnitType, List<Unit>> dummy = Arrays.stream(Unit.values())
                .collect(Collectors.groupingBy(unit -> unit.getType()));
        System.out.println(dummy);
    }
}
