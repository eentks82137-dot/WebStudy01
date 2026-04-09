package kr.or.ddit.templatemethodpattern;

public class LazyStudent extends DDITStudent {
    @Override
    protected void step2() {
        System.out.println("강의실 내에서 열심히 졸기 😴");
    }
}
