package kr.or.ddit.hw04.validation;

import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.dto.ConversionRequest;
import kr.or.ddit.hw04.exception.UnitConversionException;

/**
 * 사용자 입력을 검증하는 클래스
 *
 * 검증 항목
 * 1. 파라미터 개수
 * 2. 숫자 값
 * 3. 단위 존재 여부
 * 4. 단위 타입 매칭 여부
 */
public class ConversionValidator {

    /**
     * 
     * @param args
     * @return
     * @throws UnitConversionException
     */
    public static ConversionRequest validate(String... args) throws UnitConversionException {

        // 1. 파라미터 개수 확인
        if (args == null || args.length != 3) {
            throw new UnitConversionException(
                    "입력 형식: <value> <fromUnit> <toUnit>");
        }

        // 2. 숫자 값 검증
        double value;
        try {
            value = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            throw new UnitConversionException(
                    "첫 번째 값은 숫자여야 합니다: " + args[0]);
        }

        // 3. 단위 존재 검증
        Unit from = parseUnit(args[1]);
        Unit to = parseUnit(args[2]);

        // 4. 단위 타입 매칭 검증
        if (from.getType() != to.getType()) {
            throw new UnitConversionException(
                    "서로 다른 단위 타입입니다: " + from + " -> " + to);
        }

        // 모든 검증 통과
        return new ConversionRequest(value, from, to);
    }

    /**
     * 문자열을 Unit enum으로 변환
     */
    private static Unit parseUnit(String unit) {

        try {
            return Unit.valueOf(unit.toUpperCase());
        } catch (UnitConversionException e) {
            throw new UnitConversionException(
                    "지원하지 않는 단위: " + unit);
        }
    }
}