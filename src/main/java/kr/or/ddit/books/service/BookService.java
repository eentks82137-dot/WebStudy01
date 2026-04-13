package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.books.dao.BookDaoImpl;
import kr.or.ddit.books.dto.BookRequestDto;
import kr.or.ddit.books.dto.BookResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookService {
    BookDaoImpl bookDao = new BookDaoImpl();

    public List<BookResponseDto> getAllBooks() {
        return bookDao.selectAllBooks();
    }

    public List<BookResponseDto> searchBooks(BookRequestDto requestDto) {
        String searchData = "";
        requestDto.setSearchType(requestDto.getSearchType().toLowerCase()); // 검색 타입을 소문자로 변환하여 일관성 유지
        List<BookResponseDto> searchResults = null;
        switch (requestDto.getSearchType()) {
            case "title":
                requestDto.setSearchType("title");
                searchResults = bookDao.searchBooks(requestDto); // 제목으로 검색 수행
                break;
            case "author":
                searchData = String.valueOf(bookDao.getAuthorIdByName(requestDto.getSearchQuery())); // author 이름을
                                                                                                     // author_id로 변환
                requestDto.setSearchQuery(searchData); // searchData로 검색어 업데이트
                requestDto.setSearchType("author_id");
                searchResults = bookDao.searchBooks(requestDto); // 업데이트된 searchData로 검색 수행
                break;
            case "publisher":
                int publisherId = bookDao.getPublisherIdByName(requestDto.getSearchQuery());
                if (publisherId == 0) {
                    log.warn("No publisher found with name: {}", requestDto.getSearchQuery());
                    return null; // 또는 빈 리스트 반환
                }
                searchData = String.valueOf(publisherId); // publisher 이름을 publisher_id로 변환
                requestDto.setSearchQuery(searchData); // searchData로 검색어 업데이트
                requestDto.setSearchType("publisher_id");
                searchResults = bookDao.searchBooks(requestDto); // 업데이트된 searchData로 검색 수행
                break;
            case "isbn":
                requestDto.setSearchType("isbn");
                searchResults = bookDao.searchBooks(requestDto);
                break;
            default:
                log.warn("Unknown search type: {}", requestDto.getSearchType());
                break;
        }
        return searchResults;
    }
}
