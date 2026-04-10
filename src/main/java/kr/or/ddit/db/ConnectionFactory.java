package kr.or.ddit.db;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.datasource.impl.OracleDataSource;

@Slf4j
public class ConnectionFactory {
    static String url;
    static String username;
    static String password;
    static DataSource dataSource;

    static {
        log.info("======================= ConnectionFactory Static Block =======================");
        String basename = "kr.or.ddit.db.DbInfo";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(basename);
        url = resourceBundle.getString("url");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
        // String driverClassName = resourceBundle.getString("driverClassName");

        try {
            OracleDataSource ods = new OracleDataSource();
            dataSource = ods;

            ods.setURL(url);
            ods.setUser(username);
            ods.setPassword(password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // try {
        // Class.forName(driverClassName);
        // log.info("Driver Loading Success!!");
        // } catch (ClassNotFoundException e) {
        // throw new RuntimeException(e);
        // }
    }

    public static Connection createConnection() throws SQLException {
        // return DriverManager.getConnection(url, username, password);
        return dataSource.getConnection();
    }
}
