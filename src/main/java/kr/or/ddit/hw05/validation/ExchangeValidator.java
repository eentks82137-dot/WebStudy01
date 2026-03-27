package kr.or.ddit.hw05.validation;

import java.util.Currency;

import kr.or.ddit.hw05.dto.ExchangeRequestDTO;

public class ExchangeValidator {

    /**
     * 환전 요청 파라미터를 검증하는 메서드
     * 
     * @param params
     * @return
     * @throws RuntimeException
     */
    public ExchangeRequestDTO validate(String... params) throws RuntimeException { // RuntimeException은 unchecked
                                                                                   // exception이므로 throws를 생략할 수 있다.
                                                                                   // 하지만 명시적으로 throws를 선언하여 예외가 발생할 수
                                                                                   // 있음을 알리는 것도 좋은 방법이다.
        if (params == null || params.length != 3) {
            throw new IllegalArgumentException("잘못된 요청입니다. amount, from, to 3개의 파라미터가 필요합니다.");
        }
        double amount = 0;
        try {
            amount = Double.parseDouble(params[0]);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("amount는 숫자여야 합니다.", e); // 예외 전환
        }
        try {
            Currency from = Currency.getInstance(params[1]);
            Currency to = Currency.getInstance(params[2]);
            return new ExchangeRequestDTO(amount, from, to);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("지원하지 않는 화폐 단위입니다.", e); // 예외 전환
        }
    }
}
