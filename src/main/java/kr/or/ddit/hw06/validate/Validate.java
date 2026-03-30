package kr.or.ddit.hw06.validate;

import kr.or.ddit.hw06.dto.CalcRequestDTO;

public class Validate {
    public static CalcRequestDTO validate(String... params) {
        String operator = "";
        double left = 0;
        double right = 0;
        try {
            operator = params[0];
            left = Double.parseDouble(params[1]);
            right = Double.parseDouble(params[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
        }
        return new CalcRequestDTO(operator, left, right);
    }

    public static CalcRequestDTO validate(CalcRequestDTO requestDTO)
            throws IllegalArgumentException, NullPointerException {
        String operator = "";
        try {
            operator = requestDTO.getOperator();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("연산자가 누락되었습니다.");
        }
        double left = 0;
        double right = 0;
        try {
            left = requestDTO.getLeft();
            right = requestDTO.getRight();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다.");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("필수 필드가 누락되었습니다.");
        }
        if (operator == null || operator.isEmpty()) {
            throw new IllegalArgumentException("연산자가 누락되었습니다.");
        }
        return new CalcRequestDTO(operator, left, right);
    }
}
