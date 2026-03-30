package kr.or.ddit.hw06.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder
public class CalcResponseDTO implements Serializable {
    private double left;
    private double right;
    private String operator;
    private double result;
    private String expression;
}
