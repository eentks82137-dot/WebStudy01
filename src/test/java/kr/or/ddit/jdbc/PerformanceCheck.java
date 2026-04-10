package kr.or.ddit.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import kr.or.ddit.db.ConnectionFactory;

public class PerformanceCheck {

    // 804 ms
    @Test
    void testOneConnOneProcess() throws SQLException {
        long start = System.currentTimeMillis();
        String sql = """
                Select  mem_name, mem_mileage
                from member
                where mem_id = ?
                """;
        try (
                Connection conn = ConnectionFactory.createConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            String username = "a001";
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("mem_name");
                    String mileage = rs.getString("mem_mileage");

                    System.out.println("%s, %s".formatted(name, mileage));
                }
            } catch (Exception e) {

            }
        } catch (Exception e) {

        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    // 4660 ms
    @Test
    void test100Conn100Process() throws SQLException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String sql = """
                    Select  mem_name, mem_mileage
                    from member
                    where mem_id = ?
                    """;
            try (
                    Connection conn = ConnectionFactory.createConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);) {
                String username = "a001";
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("mem_name");
                        String mileage = rs.getString("mem_mileage");

                        System.out.println("%s, %s".formatted(name, mileage));
                    }
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    // 906 ms
    @Test
    void test1Conn100Process() throws SQLException {
        long start = System.currentTimeMillis();
        String sql = """
                Select  mem_name, mem_mileage
                from member
                where mem_id = ?
                """;
        try (
                Connection conn = ConnectionFactory.createConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            String username = "a001";
            pstmt.setString(1, username);
            for (int i = 0; i < 100; i++) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("mem_name");
                        String mileage = rs.getString("mem_mileage");

                        System.out.println("%s, %s".formatted(name, mileage));
                    }
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
