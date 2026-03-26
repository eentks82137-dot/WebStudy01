package kr.or.ddit.hw04.service;

import java.text.NumberFormat;
import java.util.Locale;

import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.domain.UnitConverter;
import kr.or.ddit.hw04.dto.ConversionRequest;
import kr.or.ddit.hw04.dto.ConversionResponse;

public class UnitConversionService {
    /**
     * 
     * @param reqDTO
     * @param locale
     * @return
     */
    public ConversionResponse convert(ConversionRequest reqDTO, Locale locale) {
        double value = reqDTO.getValue();
        Unit from = reqDTO.getFrom();
        Unit to = reqDTO.getTo();

        double result = UnitConverter.convert(value, from, to);

        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        String formattedResult = formatter.format(result);

        return ConversionResponse.builder()
                .from(from.name())
                .to(to.name())
                .value(value)
                .formattedResult(formattedResult)
                .locale(locale.toLanguageTag())
                .result(result)
                .build();
    }
}
