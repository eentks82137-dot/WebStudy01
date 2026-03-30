package kr.or.ddit.hw06.validate;

import kr.or.ddit.hw06.dto.CalcRequestDTO;

public class Validate {
    public static CalcRequestDTO validate(String... params) {
        String operator = params[0];
        double left = 0;
        double right = 0;
        try {
            left = Double.parseDouble(params[1]);
            right = Double.parseDouble(params[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다.");
        }
        return new CalcRequestDTO(operator, left, right);
    }
}
