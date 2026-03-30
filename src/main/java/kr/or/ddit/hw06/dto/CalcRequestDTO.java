package kr.or.ddit.hw06.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalcRequestDTO implements Serializable {
    private final String operator;
    private final double left;
    private final double right;
}
