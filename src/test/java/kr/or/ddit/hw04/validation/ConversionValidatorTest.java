package kr.or.ddit.hw04.validation;

import org.junit.jupiter.api.Test;

import kr.or.ddit.hw04.dto.ConversionRequest;

public class ConversionValidatorTest {
    @Test
    void testValidateInvalid() {
        try {
            ConversionValidator.validate("1", "KM", "C");
        } catch (IllegalArgumentException e) {
            System.err.println("400에러, %s".formatted(e.getMessage()));
        }
    }

    @Test
    void testValidateValid() {
        try {
            ConversionRequest reqDTO = ConversionValidator.validate("1", "KM", "MILE");
            System.out.println(reqDTO);

        } catch (IllegalArgumentException e) {
            System.err.println("400에러, %s".formatted(e.getMessage()));
        }
    }

    @Test
    void testValidateValid2() {
        try {
            ConversionRequest reqDTO = ConversionValidator.validate("1", "KM", "FT");
            System.out.println(reqDTO);

        } catch (IllegalArgumentException e) {
            System.err.println("400에러, %s".formatted(e.getMessage()));
        }
    }
}
