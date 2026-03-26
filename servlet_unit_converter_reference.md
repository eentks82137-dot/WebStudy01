# Servlet Unit Converter -- Final Reference

## Overview

This document records the final structure of the **Servlet-based unit
converter** project.

**Input (HTTP Request)**

    GET /convert?value=10&from=KM&to=MILE

**Output (JSON Response)**

```json
{
  "from": "km",
  "to": "mile",
  "value": 10,
  "result": 6.2137,
  "formattedResult": "6,213.7"
}
```

---

# Package Structure

    kr.or.ddit.hw04
     ├─ domain
     │   ├─ UnitType
     │   └─ Unit
     │
     ├─ service
     │   └─ UnitConverter
     │
     ├─ validation
     │   └─ ConversionValidator
     │
     ├─ dto
     │   ├─ ConversionRequest
     │   ├─ ConversionResponse
     │   └─ ErrorResponse
     │
     └─ web
         └─ UnitConvertServlet

---

# 1. UnitType

Defines the domain category of units.

```java
package kr.or.ddit.hw04.domain;

/**
 * Domain categories of units.
 * Units from different types cannot be converted.
 */
public enum UnitType {

    LENGTH,
    TEMPERATURE,
    WEIGHT

}
```

---

# 2. Unit

Defines the actual units.

```java
package kr.or.ddit.hw04.domain;

/**
 * Unit definitions.
 * Each unit belongs to a UnitType.
 */
public enum Unit {

    KM(UnitType.LENGTH),
    MILE(UnitType.LENGTH),

    M(UnitType.LENGTH),
    FT(UnitType.LENGTH),

    C(UnitType.TEMPERATURE),
    F(UnitType.TEMPERATURE),

    KG(UnitType.WEIGHT),
    LB(UnitType.WEIGHT);

    private final UnitType type;

    Unit(UnitType type) {
        this.type = type;
    }

    public UnitType getType() {
        return type;
    }
}
```

---

# 3. UnitConverter

Contains the conversion rules and executes the conversion.

```java
package kr.or.ddit.hw04.service;

import kr.or.ddit.hw04.domain.Unit;

import java.util.function.DoubleUnaryOperator;

/**
 * 단위 변환 규칙을 정의하는 Enum
 * 하나의 규칙이 양방향 변환을 모두 담당한다.
 */
public enum UnitConverter {

    KM_MILE(
            Unit.KM,
            Unit.MILE,
            v -> v * 0.621371,
            v -> v / 0.621371
    ),

    M_FT(
            Unit.M,
            Unit.FT,
            v -> v * 3.28084,
            v -> v / 3.28084
    ),

    C_F(
            Unit.C,
            Unit.F,
            v -> v * 9 / 5 + 32,
            v -> (v - 32) * 5 / 9
    ),

    KG_LB(
            Unit.KG,
            Unit.LB,
            v -> v * 2.20462,
            v -> v / 2.20462
    );

    private final Unit from;
    private final Unit to;

    private final DoubleUnaryOperator forward;
    private final DoubleUnaryOperator reverse;

    UnitConverter(
            Unit from,
            Unit to,
            DoubleUnaryOperator forward,
            DoubleUnaryOperator reverse) {

        this.from = from;
        this.to = to;
        this.forward = forward;
        this.reverse = reverse;
    }

    /**
     * 해당 규칙이 두 단위에 적용되는지 확인
     */
    public boolean matches(Unit a, Unit b) {
        return (a == from && b == to) || (a == to && b == from);
    }

    /**
     * 실제 값의 변환 수행
     */
    public double apply(double value, Unit a, Unit b) {

        if (a == from && b == to) {
            return forward.applyAsDouble(value);
        }

        if (a == to && b == from) {
            return reverse.applyAsDouble(value);
        }

        throw new IllegalArgumentException("지원하지 않는 변환");
    }

    /**
     * 두 단위에 맞는 변환 규칙을 반환
     */
    public static UnitConverter find(Unit from, Unit to) {
        for (UnitConverter rule : values()) {
            if (rule.matches(from, to)) {
                return rule;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 단위 변환");
    }

    /**
     * 두 단위에 맞는 변환 규칙을 찾고 변환 수행, 외부에서 사용할 변환 로직
     */
    public static double convert(double value, Unit from, Unit to) {
        if (from == to) {
            return value;
        } else {
            return find(from, to).apply(value, from, to);
        }
    }

}
```

---

# 4. ConversionRequest

Validated request data.

```java
package kr.or.ddit.hw04.dto;

import kr.or.ddit.hw04.domain.Unit;

public class ConversionRequest {

    private final double value;
    private final Unit from;
    private final Unit to;

    public ConversionRequest(double value, Unit from, Unit to) {
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public Unit getFrom() {
        return from;
    }

    public Unit getTo() {
        return to;
    }
}
```

---

# 5. ConversionValidator

Validates the request parameters.

```java
package kr.or.ddit.hw04.validation;

import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.dto.ConversionRequest;

public class ConversionValidator {

    public static ConversionRequest validate(String[] args) {

        if (args == null || args.length != 3) {
            throw new IllegalArgumentException(
                    "Input format: <value> <fromUnit> <toUnit>");
        }

        double value;

        try {
            value = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Value must be a number: " + args[0]);
        }

        Unit from = parseUnit(args[1]);
        Unit to = parseUnit(args[2]);

        if (from.getType() != to.getType()) {
            throw new IllegalArgumentException(
                    "Units are not compatible: " + from + " -> " + to);
        }

        return new ConversionRequest(value, from, to);
    }

    private static Unit parseUnit(String unit) {

        try {
            return Unit.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unsupported unit: " + unit);
        }
    }
}
```

# 6. ConversionResponse

```java
package kr.or.ddit.hw04.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class ConversionResponse implements Serializable {
    private String from;
    private String to;
    private double value;
    private double result;
    private String formattedResult;
    private String locale;
}

```

# 7. ConversionService

```java
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
                .build();
    }
}

```

---

# Request Example

    GET /hw04/convert?value=10&from=KM&to=MILE

# Response Example

```json
{
  "from": "km",
  "to": "mile",
  "value": 10,
  "result": 6.2137,
  "formattedResult": "6,213.7"
}
```
