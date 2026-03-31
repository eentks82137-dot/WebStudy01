package kr.or.ddit.hw05.service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import kr.or.ddit.hw05.domain.ConvertiblePair;
import kr.or.ddit.hw05.dto.ExchangeRequestDTO;
import kr.or.ddit.hw05.dto.ExchangeResponseDTO;

public class ExchangeService {
    @FunctionalInterface // 함수형 인터페이스는 람다식으로 구현할 수 있다. 인터페이스에 @FunctionalInterface 어노테이션을 붙이면 컴파일러가 함수형
                         // 인터페이스인지 검증해준다.
    public interface Converter { // 추상 메소드가 하나인 인터페이스는 함수형 인터페이스라고도 불린다. 람다식으로 구현할 수 있다.
        double convert(double amount);

        static Converter identity() { // IDENTITY 함수라고도 불린다. 입력값과 출력값이 같은 함수이다.
            return amount -> amount;
        }
    }

    private static double rate;

    public static final Map<ConvertiblePair, Converter> converterMap;
    static { // 클래스 초기화 블록, 클래스가 로드될 때 한 번 실행된다. static 블록은 클래스 변수(converterMap)를 초기화하는 데
             // 사용된다.
        try {
            rate = GetExchangeRate.getRate("USD", "KRW");
        } catch (IOException | InterruptedException | ParseException e) {
            rate = 1500;
            throw new RuntimeException("환율 정보를 가져오는 데 실패했습니다.", e);
            // Unchecked 예외로 감싸서 던지면, 이 예외를 처리하지 않아도 컴파일 에러가 발생하지 않는다. 런타임에 예외가 발생할 수 있지만,
            // 컴파일 시점에서는 이를 강제하지 않는다.
        }

        // rate = tempRate;

        Currency won = Currency.getInstance("KRW");
        Currency dollar = Currency.getInstance("USD");
        converterMap = new HashMap<>();
        converterMap.put(new ConvertiblePair(won, dollar), amount -> amount / rate);
        converterMap.put(new ConvertiblePair(dollar, won), amount -> amount * rate);
        converterMap.put(new ConvertiblePair(won, won), Converter.identity());
        converterMap.put(new ConvertiblePair(dollar, dollar), Converter.identity());

    }

    public List<Currency> getConvertibleCurrencies() {
        return converterMap.keySet().stream().flatMap(t -> Stream.of(t.getFrom(), t.getTo())).distinct().toList();
    }

    public ExchangeResponseDTO exchange(ExchangeRequestDTO reqDTO, Locale locale) {
        double amount = reqDTO.getAmount();
        Currency from = reqDTO.getFrom();
        Currency to = reqDTO.getTo();
        ConvertiblePair key = new ConvertiblePair(from, to);
        Converter converter = converterMap.get(key);
        if (converter == null) {
            throw new IllegalArgumentException("지원하지 않는 화폐 단위입니다.");
        }
        double convertedAmount = converter.convert(amount);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(to);
        formatter.setMaximumFractionDigits(2);
        String formattedResult = formatter.format(convertedAmount);

        return ExchangeResponseDTO.builder()
                .amount(amount)
                .from(from)
                .to(to)
                .convertedAmount(convertedAmount)
                .exchangeRate(rate)
                .formattedConvertedAmount(formattedResult)
                .build();
    }
}
