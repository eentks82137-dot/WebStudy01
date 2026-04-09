package kr.or.ddit.dummy.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "col1" }) // col1을 기준으로 equals와 hashCode 메서드 생성
public class DummyDto implements Serializable {

    private Integer col1; // Integer는 null이 될 수 있음
    private String col2;
}
