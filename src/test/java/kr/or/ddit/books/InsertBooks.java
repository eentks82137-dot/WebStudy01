package kr.or.ddit.books;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import kr.or.ddit.db.ConnectionFactoryWithPooling;
import net.datafaker.Faker;

public class InsertBooks {

    private static final int AUTHOR_COUNT = 10;
    private static final int PUBLISHER_COUNT = 6;
    private static final int BOOK_COUNT = 40;

    @Test
    void insertBooks() throws SQLException {
        Faker faker = new Faker(Locale.KOREAN);

        try (Connection conn = ConnectionFactoryWithPooling.createConnection()) {
            conn.setAutoCommit(false);
            try {
                int nextAuthorId = nextId(conn, "author", "author_id");
                int nextPublisherId = nextId(conn, "publisher", "publisher_id");
                int nextBookId = nextId(conn, "book", "book_id");
                int nextReviewId = nextId(conn, "review", "review_id");

                List<Integer> authorIds = insertAuthors(conn, faker, nextAuthorId, AUTHOR_COUNT);
                List<Integer> publisherIds = insertPublishers(conn, faker, nextPublisherId, PUBLISHER_COUNT);
                int reviewCount = insertBooksAndReviews(conn, faker, nextBookId, nextReviewId, authorIds,
                        publisherIds, BOOK_COUNT);

                conn.commit();
                System.out.printf(
                        "Inserted authors=%d, publishers=%d, books=%d, reviews=%d%n",
                        authorIds.size(),
                        publisherIds.size(),
                        BOOK_COUNT,
                        reviewCount);
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private int nextId(Connection conn, String tableName, String columnName) throws SQLException {
        String sql = String.format("select nvl(max(%s), 0) + 1 from %s", columnName, tableName);
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private List<Integer> insertAuthors(Connection conn, Faker faker, int startId, int count) throws SQLException {
        String sql = """
                insert into author (author_id, author_name, birth_date, nationality)
                values (?, ?, ?, ?)
                """;
        List<Integer> authorIds = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int index = 0; index < count; index++) {
                int authorId = startId + index;
                authorIds.add(authorId);
                pstmt.setInt(1, authorId);
                pstmt.setString(2, fit(faker.name().fullName(), 100));
                pstmt.setDate(3, Date.valueOf(randomPastDate(faker, 60, 20)));
                pstmt.setString(4, fit(faker.nation().nationality(), 50));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
        return authorIds;
    }

    private List<Integer> insertPublishers(Connection conn, Faker faker, int startId, int count) throws SQLException {
        String sql = """
                insert into publisher (publisher_id, publisher_name, address, phone)
                values (?, ?, ?, ?)
                """;
        List<Integer> publisherIds = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int index = 0; index < count; index++) {
                int publisherId = startId + index;
                publisherIds.add(publisherId);
                pstmt.setInt(1, publisherId);
                pstmt.setString(2, fit(faker.book().publisher(), 100));
                pstmt.setString(3, fit(faker.address().fullAddress(), 200));
                pstmt.setString(4, fit(faker.phoneNumber().cellPhone(), 20));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
        return publisherIds;
    }

    private int insertBooksAndReviews(
            Connection conn,
            Faker faker,
            int startBookId,
            int startReviewId,
            List<Integer> authorIds,
            List<Integer> publisherIds,
            int count) throws SQLException {
        String bookSql = """
                insert into book (book_id, title, author_id, publisher_id, isbn, publication_date, price, description)
                values (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        String reviewSql = """
                insert into review (review_id, book_id, reviewer_name, rating, content, created_date)
                values (?, ?, ?, ?, ?, ?)
                """;

        int reviewId = startReviewId;
        int reviewCount = 0;
        try (
                PreparedStatement bookStmt = conn.prepareStatement(bookSql);
                PreparedStatement reviewStmt = conn.prepareStatement(reviewSql)) {
            for (int index = 0; index < count; index++) {
                int bookId = startBookId + index;
                bookStmt.setInt(1, bookId);
                bookStmt.setString(2, fit(faker.book().title(), 200));
                bookStmt.setInt(3, pickRandomId(faker, authorIds));
                bookStmt.setInt(4, pickRandomId(faker, publisherIds));
                bookStmt.setString(5, faker.code().isbn13().replace("-", ""));
                bookStmt.setDate(6, Date.valueOf(randomPastDate(faker, 10, 1)));
                bookStmt.setInt(7, faker.number().numberBetween(12000, 45000));
                bookStmt.setString(8, faker.lorem().paragraph(2));
                bookStmt.addBatch();

                int reviewPerBook = faker.number().numberBetween(1, 4);
                for (int reviewIndex = 0; reviewIndex < reviewPerBook; reviewIndex++) {
                    reviewStmt.setInt(1, reviewId++);
                    reviewStmt.setInt(2, bookId);
                    reviewStmt.setString(3, fit(faker.name().fullName(), 50));
                    reviewStmt.setInt(4, faker.number().numberBetween(1, 6));
                    reviewStmt.setString(5, fit(faker.lorem().sentence(2), 1000));
                    reviewStmt.setDate(6, Date.valueOf(randomPastDate(faker, 2, 0)));
                    reviewStmt.addBatch();
                    reviewCount++;
                }
            }

            bookStmt.executeBatch();
            reviewStmt.executeBatch();
        }
        return reviewCount;
    }

    private int pickRandomId(Faker faker, List<Integer> ids) {
        return ids.get(faker.number().numberBetween(0, ids.size()));
    }

    private LocalDate randomPastDate(Faker faker, int maxYearsAgo, int minYearsAgo) {
        int minDays = minYearsAgo * 365;
        int maxDays = maxYearsAgo * 365;
        return LocalDate.now().minusDays(faker.number().numberBetween(minDays, maxDays));
    }

    private String fit(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
