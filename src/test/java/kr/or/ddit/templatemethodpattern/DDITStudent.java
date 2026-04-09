package kr.or.ddit.templatemethodpattern;

public abstract class DDITStudent {
    // 행동 순서(단계)가 고정된 템플릿 메소드
    public final void template() {
        step1();
        step2();
        step3();
    }

    private void step1() {
        System.out.println("지문 인체킹");
    }

    // 고정 단계 내에서 케이스별로 다른 행동이 존재하는 경우, 해당 행동을 추상화한 메소드 : hook 메소드
    protected abstract void step2();

    private void step3() {
        System.out.println("지문 아웃체킹");
    }
}
