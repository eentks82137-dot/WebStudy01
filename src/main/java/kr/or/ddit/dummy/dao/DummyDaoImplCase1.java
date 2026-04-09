package kr.or.ddit.dummy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.dummy.dto.DummyDto;

public class DummyDaoImplCase1 implements DummyDao {

    @Override
    public int insertDummy(DummyDto dummy) {
        // 1. 쿼리 작성 *
        String sql = """
                insert into dummy
                (col1, col2)
                values (?, ?)
                """;
        // 2. Connection 생성
        try (Connection conn = ConnectionFactory.createConnection();
                // 3. 쿼리 객체 생성
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            // 4. 쿼리 파라미터 설정 *
            pstmt.setInt(1, dummy.getCol1());
            pstmt.setString(2, dummy.getCol2());
            // 5. 쿼리 실행
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public DummyDto selectDummy(int col1) {
        // 1. 쿼리 작성 *
        String sql = """
                select col1, col2
                from dummy
                where col1 = ?
                """;
        DummyDto one = null;
        // 2. Connection 생성
        try (Connection conn = ConnectionFactory.createConnection();
                // 3. 쿼리 객체 생성
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            // 4. 쿼리 파라미터 설정 *
            pstmt.setInt(1, col1);
            // 5. 쿼리 실행
            try (ResultSet rs = pstmt.executeQuery()) {
                // 6. 조회 결과 바인딩 * (Single row 바인드)
                if (rs.next()) {
                    one = new DummyDto();
                    one.setCol1(rs.getInt("col1"));
                    one.setCol2(rs.getString("col2"));
                }
                return one;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<DummyDto> selectDummyList() {
        // 1. 쿼리 작성 *
        String sql = """
                select col1, col2
                from dummy
                """;
        List<DummyDto> list = new ArrayList<>();
        // 2. Connection 생성
        try (Connection conn = ConnectionFactory.createConnection();
                // 3. 쿼리 객체 생성
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            // 4. 쿼리 파라미터 설정 *
            // 5. 쿼리 실행
            try (ResultSet rs = pstmt.executeQuery()) {
                // 6. 조회 결과 바인딩 * (Single row 바인드)
                while (rs.next()) {
                    DummyDto dto = new DummyDto();
                    dto.setCol1(rs.getInt("col1"));
                    dto.setCol2(rs.getString("col2"));
                    list.add(dto);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int deleteDummy(int col1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDummy'");
    }
}
