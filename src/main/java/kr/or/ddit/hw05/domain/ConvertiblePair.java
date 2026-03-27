package kr.or.ddit.hw05.domain;

import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(of = { "from", "to" }) // from과 to가 같으면 같은 객체로 간주하겠다.
public class ConvertiblePair {
    private final Currency from;
    private final Currency to;
}
