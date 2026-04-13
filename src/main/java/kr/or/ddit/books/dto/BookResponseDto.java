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
public class BookResponseDto implements Serializable {
    private Integer bookId;
    private String title;
    private String author; // author_id 대신 author 이름을 담는 필드
    private String publisher; // publisher_id 대신 publisher 이름을 담는 필드
    private String isbn;
    private LocalDate publicationDate;
    private Integer price;
    private String description;
}
