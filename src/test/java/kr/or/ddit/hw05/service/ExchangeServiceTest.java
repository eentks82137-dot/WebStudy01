package kr.or.ddit.hw05.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import kr.or.ddit.hw05.dto.ExchangeRequestDTO;
import kr.or.ddit.hw05.dto.ExchangeResponseDTO;

public class ExchangeServiceTest {
    ExchangeService service = new ExchangeService();

    @Test
    void testExchange() {
        ExchangeRequestDTO requestDTO = new ExchangeRequestDTO(1, Currency.getInstance("USD"),
                Currency.getInstance("KRW"));
        ExchangeResponseDTO responseDTO = service.exchange(requestDTO, Locale.US);
        System.out.println(responseDTO);
        assertEquals(1500d, responseDTO.getConvertedAmount());

        requestDTO = new ExchangeRequestDTO(1500, Currency.getInstance("KRW"),
                Currency.getInstance("USD"));
        responseDTO = service.exchange(requestDTO, Locale.US);
        System.out.println(responseDTO);
        assertEquals(1d, responseDTO.getConvertedAmount());

    }

    @Test
    void testCurrencies() {
        List<Currency> currencies = service.getConvertibleCurrencies();

        assertEquals(2, currencies.size());

        System.out.println(currencies);
    }
}
