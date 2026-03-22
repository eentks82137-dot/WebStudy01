package kr.or.ddit.hw03;

import java.text.NumberFormat;
import java.util.Locale;

public class UnitConverter {
    public static double converter(String from, String to, String value) {
        if (from == null || to == null || value == null || from.isBlank() || to.isBlank() || value.isBlank()) {
            throw new IllegalArgumentException("파라미터가 누락되었습니다");
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("value는 숫자여야 합니다: " + value, e);
        }

        if ("km".equals(from) && "mile".equals(to)) {
            return inputValue * 0.621371;
        } else if ("mile".equals(from) && "km".equals(to)) {
            return inputValue / 0.621371;
        } else if ("m".equals(from) && "ft".equals(to)) {
            return inputValue * 3.28084;
        } else if ("ft".equals(from) && "m".equals(to)) {
            return inputValue / 3.28084;
        } else if ("C".equals(from) && "F".equals(to)) {
            return inputValue * 9 / 5 + 32;
        } else if ("F".equals(from) && "C".equals(to)) {
            return (inputValue - 32) * 5 / 9;
        } else if ("kg".equals(from) && "lb".equals(to)) {
            return inputValue * 2.20462;
        } else if ("lb".equals(from) && "kg".equals(to)) {
            return inputValue / 2.20462;
        }

        throw new IllegalArgumentException("지원하지 않는 단위 변환입니다: " + from + " -> " + to);
    }

    public static String format(double convertedValue, Locale locale) {
        Locale targetLocale = locale == null ? Locale.getDefault() : locale;
        return NumberFormat.getInstance(targetLocale).format(convertedValue);
    }
}
