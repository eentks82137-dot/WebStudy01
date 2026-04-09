package kr.or.ddit.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.props.dto.DataBaseProperty;

/**
 * JDBC (Java DataBase Connectivity)
 * 
 * 1. 데이터베이스 벤더에서 제공하는 드라이버 검색
 * 2. 드라이버를 빌드 패스에 추가
 * 3. 드라이버 로딩
 * 4. Connection 생성
 * 5. Query 객체
 * 6. Query 실행 : ResultSet
 * 7. 사용된 자원 해제(ResultSet, 쿼리객체, Connection 등)
 */
public class JdbcCodeTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("======================= Before All =======================");
    }

    @BeforeEach
    void setUp() {
        System.out.println("======================= Before Each =======================");
        System.out.println("Test Data Setup...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("======================= After Each =======================");
        System.out.println("Test Data Cleanup...");
    }

    @Test
    void test() {
        String sql = """
                select property_name, property_value , description
                from database_properties
                        """;
        List<DataBaseProperty> dataBaseProperties = new ArrayList<>();
        try (Connection conn = ConnectionFactory.createConnection();
                Statement stmt = conn.createStatement();) {
            try (ResultSet rs = stmt.executeQuery(sql);) {
                while (rs.next()) {
                    dataBaseProperties.add(
                            DataBaseProperty.builder().propertyName(rs.getString("property_name"))
                                    .propertyValue(rs.getString("property_value"))
                                    .description(rs.getString("description")).build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 전체 프로퍼티 정보를 출력
        dataBaseProperties.forEach(System.out::println);
    }

    @Test
    void test2() {
        String sql = """
                select mem_id, mem_pass, mem_name
                from member
                        """;
        List<MemberDTO> dataBaseProperties = new ArrayList<>();
        try (Connection conn = ConnectionFactory.createConnection();
                Statement stmt = conn.createStatement();) {
            try (ResultSet rs = stmt.executeQuery(sql);) {
                while (rs.next()) {
                    dataBaseProperties.add(
                            MemberDTO.builder()
                                    .memId(rs.getString("mem_id"))
                                    .memName(rs.getString("mem_name"))
                                    .memPass(rs.getString("mem_pass"))
                                    .memRoles(Collections.emptyList())
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 전체 프로퍼티 정보를 출력
        dataBaseProperties.forEach(System.out::println);
    }

    @Test
    Object test3() {

        return null;
    }
}
