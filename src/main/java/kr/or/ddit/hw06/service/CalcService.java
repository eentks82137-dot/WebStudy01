package kr.or.ddit.hw06.service;

import kr.or.ddit.hw06.dto.CalcRequestDTO;
import kr.or.ddit.hw06.dto.CalcResponseDTO;

public class CalcService {

    public CalcResponseDTO calculate(CalcRequestDTO reqDTO) {
        String operSymbol;
        double result = switch (reqDTO.getOperator()) {
            case "PLUS" -> {
                operSymbol = "+";
                yield reqDTO.getLeft() + reqDTO.getRight();
            }
            case "MINUS" -> {
                operSymbol = "-";
                yield reqDTO.getLeft() - reqDTO.getRight();
            }
            case "MULTIPLY" -> {
                operSymbol = "*";
                yield reqDTO.getLeft() * reqDTO.getRight();
            }
            case "DIVIDE" -> {
                operSymbol = "/";
                if (reqDTO.getRight() == 0) {
                    throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                }
                yield reqDTO.getLeft() / reqDTO.getRight();
            }
            default -> throw new IllegalArgumentException("지원하지 않는 연산자입니다.");
        };

        String expression = String.format("%s %s %s = %s", reqDTO.getLeft(), operSymbol, reqDTO.getRight(),
                result);
        return CalcResponseDTO.builder()
                .left(reqDTO.getLeft())
                .right(reqDTO.getRight())
                .operator(reqDTO.getOperator())
                .result(result)
                .expression(expression)
                .build();
    }
}
