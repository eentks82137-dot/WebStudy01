package kr.or.ddit.hw05;

import java.util.Currency;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class CurrencyTest {

    @Test
    public void test() {
        Currency won = Currency.getInstance(Locale.KOREA);
        System.out.println(won.getCurrencyCode());
        System.out.println(won.getDisplayName());
        System.out.println(won.getSymbol());

        Currency usd = Currency.getInstance(Locale.US);
        System.out.println(usd.getCurrencyCode());
        System.out.println(usd.getDisplayName());
        System.out.println(usd.getSymbol());
    }
}
