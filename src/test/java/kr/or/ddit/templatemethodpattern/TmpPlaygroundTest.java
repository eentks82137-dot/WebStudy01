package kr.or.ddit.templatemethodpattern;

import org.junit.jupiter.api.Test;

/**
 * 
 * 1단계. 지문 인체킹
 * 2단계. 강의실 내에서의 행동은 케이스 별로 달라짐
 * 3단계. 지문 아웃체킹
 * 
 * abstract class DDITStudent {
 * 1, 2, 3단계를 고정 (final)
 * 2단계는 추상화 되어있어야 함 (abstract)
 * }
 * 
 * class GoodStudent extends DDITStudent {
 * 2단계 구체화
 * }
 * 
 * class BadStudent extends DDITStudent {
 * 2단계 구체화
 * }
 */

public class TmpPlaygroundTest {

    @Test
    void test() {
        DDITStudent[] students = new DDITStudent[] {
                new GoodStudent(),
                new LazyStudent()
        };

        for (DDITStudent student : students) {
            student.template();
        }
    }
}
