package kr.or.ddit.hw04.dto;

import java.io.Serializable;

import kr.or.ddit.hw04.domain.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 검증이 완료된 변환 요청 정보를 담는 객체
 */
@AllArgsConstructor
@Data
public class ConversionRequest implements Serializable {
    private final double value;
    private final Unit from;
    private final Unit to;
}