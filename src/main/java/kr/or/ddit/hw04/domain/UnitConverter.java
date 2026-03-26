package kr.or.ddit.hw04.domain;

import java.util.function.DoubleUnaryOperator;

import kr.or.ddit.hw04.exception.UnitConversionException;

/**
 * 단위 변환 규칙을 정의하는 Enum
 * 하나의 규칙이 양방향 변환을 모두 담당한다.
 */
public enum UnitConverter {

    KM_MILE(
            Unit.KM,
            Unit.MILE,
            v -> v * 0.621371,
            v -> v / 0.621371),

    M_FT(
            Unit.M,
            Unit.FT,
            v -> v * 3.28084,
            v -> v / 3.28084),

    C_F(
            Unit.C,
            Unit.F,
            v -> v * 9 / 5 + 32,
            v -> (v - 32) * 5 / 9),

    KG_LB(
            Unit.KG,
            Unit.LB,
            v -> v * 2.20462,
            v -> v / 2.20462);

    private final Unit from;
    private final Unit to;

    private final DoubleUnaryOperator forward;
    private final DoubleUnaryOperator reverse;

    UnitConverter(
            Unit from,
            Unit to,
            DoubleUnaryOperator forward,
            DoubleUnaryOperator reverse) {

        this.from = from;
        this.to = to;
        this.forward = forward;
        this.reverse = reverse;
    }

    /**
     * 해당 규칙이 두 단위에 적용되는지 확인
     */
    public boolean matches(Unit a, Unit b) {
        return (a == from && b == to) || (a == to && b == from);
    }

    /**
     * 실제 값의 변환 수행
     */
    public double apply(double value, Unit a, Unit b) {

        if (a == from && b == to) {
            return forward.applyAsDouble(value);
        }

        if (a == to && b == from) {
            return reverse.applyAsDouble(value);
        }

        throw new UnitConversionException("지원하지 않는 변환");
    }

    /**
     * 두 단위에 맞는 변환 규칙을 반환
     */
    public static UnitConverter find(Unit from, Unit to) {
        for (UnitConverter rule : values()) {
            if (rule.matches(from, to)) {
                return rule;
            }
        }
        throw new UnitConversionException("지원하지 않는 단위 변환");
    }

    /**
     * 두 단위에 맞는 변환 규칙을 찾고 변환 수행, 외부에서 사용할 변환 로직
     */
    public static double convert(double value, Unit from, Unit to) {
        if (from == to) {
            return value;
        } else {
            return find(from, to).apply(value, from, to);
        }
    }

}