package kr.or.ddit.servlet03.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

// @AllArgsConstructor 
@RequiredArgsConstructor // final 키워드가 붙은것만 생성자를 만들어줌
@Data
public class EchoRequest implements Serializable {
    private final String message;
    private final String language;
    private String dummy;
}
