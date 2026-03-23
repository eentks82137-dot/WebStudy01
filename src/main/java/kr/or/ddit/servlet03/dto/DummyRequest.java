package kr.or.ddit.servlet03.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DummyRequest implements Serializable {
    private final String name;
    private final int age;
}
