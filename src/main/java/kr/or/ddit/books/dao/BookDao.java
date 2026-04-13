package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.books.dto.BookRequestDto;
import kr.or.ddit.books.dto.BookResponseDto;

public interface BookDao {

    public List<BookResponseDto> selectAllBooks();

    public List<BookResponseDto> searchBooks(BookRequestDto requestDto);

    public int getAuthorIdByName(String authorName);

    public int getPublisherIdByName(String publisherName);
}
