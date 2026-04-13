package kr.or.ddit.books.dao;

import java.util.List;

import org.apache.commons.lang3.function.Failable;

import kr.or.ddit.books.dto.Author;
import kr.or.ddit.books.dto.Book;
import kr.or.ddit.books.dto.BookRequestDto;
import kr.or.ddit.books.dto.BookResponseDto;
import kr.or.ddit.books.dto.Publisher;
import kr.or.ddit.db.template.JdbcTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookDaoImpl implements BookDao {
        private JdbcTemplate jdbcTemplate = new JdbcTemplate();

        private String resolveSearchColumn(String searchType) {
                return switch (searchType) {
                        case "title" -> "title";
                        case "author_id" -> "author_id";
                        case "publisher_id" -> "publisher_id";
                        case "isbn" -> "isbn";
                        default -> throw new IllegalArgumentException("Unsupported search type: " + searchType);
                };
        }

        @Override
        public List<BookResponseDto> selectAllBooks() {
                String sql = """
                                select book_id, title, author_id, publisher_id, isbn, publication_date, price, description
                                 from book
                                """;
                List<Book> list = jdbcTemplate.queryForList(sql, null, Failable.asFunction(rs -> {
                        int bookId = rs.getInt("book_id");
                        String title = rs.getString("title");
                        int authorId = rs.getInt("author_id");
                        int publisherId = rs.getInt("publisher_id");
                        String isbn = rs.getString("isbn");
                        java.sql.Date publicationDate = rs.getDate("publication_date");
                        int price = rs.getInt("price");
                        String description = rs.getString("description");
                        return Book.builder()
                                        .bookId(bookId)
                                        .title(title)
                                        .authorId(authorId)
                                        .publisherId(publisherId)
                                        .isbn(isbn)
                                        .publicationDate(publicationDate.toLocalDate())
                                        .price(price)
                                        .description(description)
                                        .build();
                }));
                List<Author> authors = jdbcTemplate.queryForList("select author_id, author_name from author", null,
                                Failable.asFunction(rs -> {
                                        int authorId = rs.getInt("author_id");
                                        String authorName = rs.getString("author_name");
                                        return Author.builder()
                                                        .authorId(authorId)
                                                        .authorName(authorName)
                                                        .build();
                                }));

                List<Publisher> publishers = jdbcTemplate.queryForList(
                                "select publisher_id, publisher_name from publisher",
                                null, Failable.asFunction(rs -> {
                                        int publisherId = rs.getInt("publisher_id");
                                        String publisherName = rs.getString("publisher_name");
                                        return Publisher.builder()
                                                        .publisherId(publisherId)
                                                        .publisherName(publisherName)
                                                        .build();
                                }));

                List<BookResponseDto> responseList = list.stream()
                                .map(book -> {
                                        String authorName = authors.stream()
                                                        .filter(author -> author.getAuthorId()
                                                                        .equals(book.getAuthorId()))
                                                        .map(Author::getAuthorName)
                                                        .findFirst()
                                                        .orElse("Unknown Author");
                                        String publisherName = publishers.stream()
                                                        .filter(publisher -> publisher.getPublisherId()
                                                                        .equals(book.getPublisherId()))
                                                        .map(Publisher::getPublisherName)
                                                        .findFirst()
                                                        .orElse("Unknown Publisher");
                                        return BookResponseDto.builder()
                                                        .bookId(book.getBookId())
                                                        .title(book.getTitle())
                                                        .author(authorName)
                                                        .publisher(publisherName)
                                                        .isbn(book.getIsbn())
                                                        .publicationDate(book.getPublicationDate())
                                                        .price(book.getPrice())
                                                        .description(book.getDescription())
                                                        .build();
                                })
                                .toList();

                return responseList;

        }

        @Override
        public List<BookResponseDto> searchBooks(BookRequestDto requestDto) {
                String searchColumn = resolveSearchColumn(requestDto.getSearchType());
                boolean exactMatch = "author_id".equals(searchColumn) || "publisher_id".equals(searchColumn);
                String sql = exactMatch
                                ? """
                                                select book_id, title, author_id, publisher_id, isbn, publication_date, price, description
                                                 from book
                                                 where 1=1
                                                """
                                                + " and " + searchColumn + " = ?"
                                : """
                                                select book_id, title, author_id, publisher_id, isbn, publication_date, price, description
                                                 from book
                                                 where 1=1
                                                """
                                                + " and " + searchColumn + " like '%' || ? || '%'";

                List<Book> list = jdbcTemplate.queryForList(sql, Failable.asConsumer(pstmt -> {
                        pstmt.setString(1, requestDto.getSearchQuery());
                }), Failable.asFunction(rs -> {
                        int bookId = rs.getInt("book_id");
                        String title = rs.getString("title");
                        int authorId = rs.getInt("author_id");
                        int publisherId = rs.getInt("publisher_id");
                        String isbn = rs.getString("isbn");
                        java.sql.Date publicationDate = rs.getDate("publication_date");
                        int price = rs.getInt("price");
                        String description = rs.getString("description");
                        return Book.builder()
                                        .bookId(bookId)
                                        .title(title)
                                        .authorId(authorId)
                                        .publisherId(publisherId)
                                        .isbn(isbn)
                                        .publicationDate(publicationDate.toLocalDate())
                                        .price(price)
                                        .description(description)
                                        .build();
                }));

                List<Author> authors = jdbcTemplate.queryForList("select author_id, author_name from author", null,
                                Failable.asFunction(rs -> {
                                        int authorId = rs.getInt("author_id");
                                        String authorName = rs.getString("author_name");
                                        return Author.builder()
                                                        .authorId(authorId)
                                                        .authorName(authorName)
                                                        .build();
                                }));

                List<Publisher> publishers = jdbcTemplate.queryForList(
                                "select publisher_id, publisher_name from publisher",
                                null, Failable.asFunction(rs -> {
                                        int publisherId = rs.getInt("publisher_id");
                                        String publisherName = rs.getString("publisher_name");
                                        return Publisher.builder()
                                                        .publisherId(publisherId)
                                                        .publisherName(publisherName)
                                                        .build();
                                }));

                return list.stream()
                                .map(book -> {
                                        String authorName = authors.stream()
                                                        .filter(author -> author.getAuthorId()
                                                                        .equals(book.getAuthorId()))
                                                        .map(Author::getAuthorName)
                                                        .findFirst()
                                                        .orElse("Unknown Author");
                                        String publisherName = publishers.stream()
                                                        .filter(publisher -> publisher.getPublisherId()
                                                                        .equals(book.getPublisherId()))
                                                        .map(Publisher::getPublisherName)
                                                        .findFirst()
                                                        .orElse("Unknown Publisher");
                                        return BookResponseDto.builder()
                                                        .bookId(book.getBookId())
                                                        .title(book.getTitle())
                                                        .author(authorName)
                                                        .publisher(publisherName)
                                                        .isbn(book.getIsbn())
                                                        .publicationDate(book.getPublicationDate())
                                                        .price(book.getPrice())
                                                        .description(book.getDescription())
                                                        .build();
                                })
                                .toList();
        }

        @Override
        public int getAuthorIdByName(String authorName) {
                String sql = "select author_id from author where author_name like '%' || ? || '%'";
                try {
                        return jdbcTemplate.queryForObject(sql, Failable.asConsumer(pstmt -> {
                                pstmt.setString(1, authorName);
                        }), Failable.asFunction(rs -> rs.getInt("author_id")));
                } catch (Exception e) {
                        log.error("Error fetching author ID for name '{}': {}", authorName, e.getMessage());
                        return 0; // 또는 적절한 기본값 반환
                }
        }

        @Override
        public int getPublisherIdByName(String publisherName) {
                String sql = "select publisher_id from publisher where publisher_name like '%' || ? || '%'";
                try {
                        return jdbcTemplate.queryForObject(sql, Failable.asConsumer(pstmt -> {
                                pstmt.setString(1, publisherName);
                        }), Failable.asFunction(rs -> rs.getInt("publisher_id")));
                } catch (Exception e) {
                        log.error("Error fetching publisher ID for name '{}': {}", publisherName, e.getMessage());
                        return 0; // 또는 적절한 기본값 반환
                }
        }
}
