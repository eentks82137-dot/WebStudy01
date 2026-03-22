package kr.or.ddit.hw03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterTest {
    // 조건: 0을 입력하면 변환 결과도 0이어야 한다
    // 이유: 0은 어떤 단위로 변환해도 0이다
    @Test
    void convert_zero_km_to_mile() {
        double result = UnitConverter.converter("km", "mile", "0");

        Assertions.assertEquals(0.0, result);
    }

    // 조건: 숫자가 아닌 문자열을 입력하면 예외가 발생해야 한다
    // 이유: 사용자 입력을 검증하지 않으면 NumberFormatException이 서블릿까지 전파된다
    @Test
    void validate_non_numeric_value() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> UnitConverter.converter("km", "mile", "abc"));

        Assertions.assertEquals("value는 숫자여야 합니다: abc", exception.getMessage());
    }

    // 조건: km -> mile 변환 결과가 올바른가
    // 이유: 정방향 길이 변환이 올바른지 확인하기 위해
    @Test
    void validate_km_to_mile() {
        double result = UnitConverter.converter("km", "mile", "10");
        Assertions.assertEquals(6.21371, result, 0.000001);
    }

    // 조건: 10 mile을 km으로 변환하면 약 16.0934여야 한다
    // 이유: 역방향 변환도 정상 동작해야 실제 사용이 가능하기 때문에
    @Test
    void validate_mile_to_km() {
        double result = UnitConverter.converter("mile", "km", "10");

        Assertions.assertEquals(16.093444978925632, result, 0.000001);
    }

    // 음수 입력 -10C -> 14F
    // 이유: 음수가 입력되어도 정상적으로 변환이 되어야 한다
    @Test
    void convert_neg_value() {
        double result = UnitConverter.converter("C", "F", "-10");
        Assertions.assertEquals(14.0, result);
    }

    // 조건: 매우 큰 수 999999999km를 mile로 변환할 수 있어야 한다
    // 이유: 큰 수가 들어와도 오버플로 없이 계산이 수행되어야 하기 때문이다
    @Test
    void convert_large_km_to_mile() {
        double result = UnitConverter.converter("km", "mile", "999999999");

        Assertions.assertEquals(621370999.378629, result, 0.000001);
    }

    // 조건: km에서 C로 변환 요청하면 예외가 발생해야 한다
    // 이유: 서로 다른 단위의 조합은 허용되지 않아야 한다
    @Test
    void reject_unsupported_unit_pair() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> UnitConverter.converter("km", "C", "10"));

        Assertions.assertEquals("지원하지 않는 단위 변환입니다: km -> C", exception.getMessage());
    }

    // 조건: 6213.71을 독일 로케일로 포맷하면 6.213,71이 되어야 한다
    // 이유: 로케일별 숫자 포맷이 올바른지 확인하기 위해
    @Test
    void format_number_in_german_locale() {
        String formatted = UnitConverter.format(6213.71, java.util.Locale.GERMANY);

        Assertions.assertEquals("6.213,71", formatted);
    }

    // 조건: 빈 문자열이 입력되면 예외가 발생해야 한다
    // 이유: 빈 입력은 유효한 숫자가 아니므로 변환 로직까지 넘어가면 안되기 때문
    @Test
    void reject_blank_value() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> UnitConverter.converter("km", "mile", ""));

        Assertions.assertEquals("파라미터가 누락되었습니다", exception.getMessage());
    }
}
