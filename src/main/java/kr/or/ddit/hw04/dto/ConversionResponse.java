package kr.or.ddit.hw04.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class ConversionResponse implements Serializable {
    private String from;
    private String to;
    private double value;
    private double result;
    private String formattedResult;
    private String locale;
}
