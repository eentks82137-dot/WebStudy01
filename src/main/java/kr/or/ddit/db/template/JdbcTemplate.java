package kr.or.ddit.db.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import kr.or.ddit.db.ConnectionFactoryWithPooling;

public class JdbcTemplate {
    public final int update(String sql,
            Consumer<PreparedStatement> parameterMapper) {

        // 2. Connection 생성
        try (Connection conn = ConnectionFactoryWithPooling.createConnection();
                // 3. 쿼리 객체 생성
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            // 4. 쿼리 파라미터 설정 *
            if (parameterMapper != null) {
                parameterMapper.accept(pstmt);
            }
            // 5. 쿼리 실행
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public final <T> T queryForObject(String sql,
            Consumer<PreparedStatement> parameterMapper,
            Function<ResultSet, T> rowMapper) {
        List<T> list = queryForList(sql, parameterMapper, rowMapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.getFirst();
        }
    }

    public final <T> List<T> queryForList(String sql,
            Consumer<PreparedStatement> parameterMapper,
            Function<ResultSet, T> rowMapper) {
        // 1. 쿼리 작성 *
        List<T> list = new ArrayList<>();
        // 2. Connection 생성
        try (Connection conn = ConnectionFactoryWithPooling.createConnection();
                // 3. 쿼리 객체 생성
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            // 4. 쿼리 파라미터 설정 *
            if (parameterMapper != null) {
                parameterMapper.accept(pstmt);
            }
            // 5. 쿼리 실행
            try (ResultSet rs = pstmt.executeQuery()) {
                // 6. 조회 결과 바인딩 * (Single row 바인드)
                while (rs.next()) {
                    T dto = rowMapper.apply(rs);
                    list.add(dto);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
