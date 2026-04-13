package kr.or.ddit.books.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author implements Serializable {
    private Integer authorId;
    private String authorName;
    private LocalDate birthDate;
    private String nationality;
}
