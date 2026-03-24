package kr.or.ddit.servlet03.service;

import java.time.LocalDateTime;

import kr.or.ddit.servlet03.dto.EchoRequest;
import kr.or.ddit.servlet03.dto.EchoResponse;

public class EchoService {
    public EchoResponse processEcho(EchoRequest reqDto) {
        String original = reqDto.getMessage();
        String languageTag = reqDto.getLanguage();

        return EchoResponse.builder()
                .original(original)
                .echoed(String.format("%s, 메아리", original))
                .length(original.length())
                .receivedAt(LocalDateTime.now().toString())
                .build();

        // EchoResponse respDto = new EchoResponse();
        // respDto.setOriginal(original);
        // respDto.setEchoed(String.format("%s, 메아리", original));
        // respDto.setLength(original.length());
        // respDto.setReceivedAt(LocalDateTime.now().toString());
    }
}
