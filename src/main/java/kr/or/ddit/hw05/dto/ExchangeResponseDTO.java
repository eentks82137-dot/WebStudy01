package kr.or.ddit.hw05.dto;

import java.io.Serializable;
import java.util.Currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeResponseDTO implements Serializable {
    private double amount;
    private Currency from;
    private Currency to;

    private double exchangeRate;
    private double convertedAmount;
    private String formattedConvertedAmount;
}
