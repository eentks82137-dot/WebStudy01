package kr.or.ddit.hw04.domain;

/**
 * 실제 단위를 정의하는 Enum
 * 각 단위는 하나의 UnitType에 속한다.
 */
public enum Unit {

    KM(UnitType.LENGTH),
    MILE(UnitType.LENGTH),

    M(UnitType.LENGTH),
    FT(UnitType.LENGTH),

    C(UnitType.TEMPERATURE),
    F(UnitType.TEMPERATURE),

    KG(UnitType.WEIGHT),
    LB(UnitType.WEIGHT);

    private final UnitType type;

    /**
     * enum 의 생성자는 묵시적으로 private 선언되므로
     * enum 외부에서 객체 생성이 불가능하다.
     */
    Unit(UnitType type) {
        this.type = type;
    }

    /**
     * 단위의 타입 반환
     */
    public UnitType getType() {
        return type;
    }

}