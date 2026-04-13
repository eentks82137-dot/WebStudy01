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
public class Review implements Serializable {
    private Integer reviewId;
    private Integer bookId;
    private String reviewerName;
    private Integer rating;
    private String content;
    private LocalDate createdDate;
}
