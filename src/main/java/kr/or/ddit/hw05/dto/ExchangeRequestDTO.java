package kr.or.ddit.hw05.dto;

import java.io.Serializable;
import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExchangeRequestDTO implements Serializable {
    private final double amount;
    private final Currency from;
    private final Currency to;
}
