package kr.or.ddit;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

public class LocaleTimeZoneTest {

    void test1() {
        Locale[] locales = Locale.getAvailableLocales();
        Map<String, String> localeMap = new HashMap<>();
        localeMap.putAll(
                Arrays.stream(locales).collect(Collectors.toMap(l -> l.toLanguageTag(), l -> l.getDisplayName(l))));
        for (Locale locale : locales) {
            String languageTag = locale.toLanguageTag();
            String displayName = locale.getDisplayLanguage(locale);
            if (displayName.isBlank())
                continue;
            localeMap.put(languageTag, displayName);
            System.out.printf("%s: %s%n", languageTag, displayName);
        }
    }

    void test2() {
        System.out.println("LocaleTimeZoneTest 실행");
        Assertions.assertEquals(1, 1);
    }

    void test3() {
        Map<String, String> timeZoneMap = new HashMap<>();
        timeZoneMap.putAll(ZoneId.getAvailableZoneIds().stream()
                .collect(Collectors.toMap(
                        z -> z,
                        z -> ZoneId.of(z).getDisplayName(TextStyle.FULL, Locale.getDefault()))));
    }

}
