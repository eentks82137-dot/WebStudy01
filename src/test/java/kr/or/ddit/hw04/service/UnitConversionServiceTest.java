package kr.or.ddit.hw04.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.dto.ConversionRequest;
import kr.or.ddit.hw04.dto.ConversionResponse;

public class UnitConversionServiceTest {

    private UnitConversionService service;
    private static final Locale TEST_LOCALE = Locale.US;

    @BeforeEach
    void setUp() {
        service = new UnitConversionService();
    }

    @Test
    void kmToMile_정방향변환() {
        ConversionRequest req = new ConversionRequest(10, Unit.KM, Unit.MILE);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals("KM", res.getFrom());
        assertEquals("MILE", res.getTo());
        assertEquals(10, res.getValue(), 0.0001);
        assertEquals(6.21371, res.getResult(), 0.0001);
    }

    @Test
    void mileToKm_역방향변환() {
        ConversionRequest req = new ConversionRequest(10, Unit.MILE, Unit.KM);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals("MILE", res.getFrom());
        assertEquals("KM", res.getTo());
        assertEquals(10, res.getValue(), 0.0001);
        assertEquals(16.0934, res.getResult(), 0.001);
    }

    @Test
    void zeroInput_경계값_0() {
        ConversionRequest req = new ConversionRequest(0, Unit.KM, Unit.MILE);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals(0, res.getResult(), 0.0001);
        assertEquals("0", res.getFormattedResult());
    }

    @Test
    void negativeInput_경계값_음수() {
        // -10°C = (-10 * 9/5) + 32 = 14°F
        ConversionRequest req = new ConversionRequest(-10, Unit.C, Unit.F);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals(-10, res.getValue(), 0.0001);
        assertEquals(14, res.getResult(), 0.0001);
    }

    @Test
    void veryLargeValue_경계값_큰수() {
        // 999999999 km * 0.621371 = 621,370,999.378629
        ConversionRequest req = new ConversionRequest(999999999, Unit.KM, Unit.MILE);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals(621370999.378629, res.getResult(), 1.0);
        assertEquals("621,370,999.379", res.getFormattedResult());
    }

    @Test
    void verySmallDecimal_경계값_작은소수() {
        // 0.0001 m * 3.28084 = 0.000328084 ft
        ConversionRequest req = new ConversionRequest(0.0001, Unit.M, Unit.FT);
        ConversionResponse res = service.convert(req, TEST_LOCALE);

        assertEquals(0.000328084, res.getResult(), 0.000001);
    }

    @Test
    void testSameUnitTypeInValid() {
        ConversionRequest req = new ConversionRequest(1, Unit.KM, Unit.FT);
        service.convert(req, TEST_LOCALE);
    }
}
