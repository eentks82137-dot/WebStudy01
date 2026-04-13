package kr.or.ddit.books.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publisher implements Serializable {
    private Integer publisherId;
    private String publisherName;
    private String address;
    private String phone;
}
