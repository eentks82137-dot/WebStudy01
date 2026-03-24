package kr.or.ddit.servlet03.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // -> builder 패턴 적용
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EchoResponse implements Serializable {
    private String original;
    private String echoed;
    private int length;
    private String receivedAt;
}
