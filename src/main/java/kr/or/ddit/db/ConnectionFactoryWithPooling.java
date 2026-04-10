package kr.or.ddit.db;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionFactoryWithPooling {
    static String url;
    static String username;
    static String password;
    static DataSource dataSource;
    static String maximumPoolSize;
    static String minimumIdle;
    static String connectionTimeoutMs;

    static String driverClassName;

    static {
        log.info("======================= ConnectionFactory Static Block =======================");
        String basename = "kr.or.ddit.db.DbInfo";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(basename);
        url = resourceBundle.getString("url");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
        maximumPoolSize = resourceBundle.getString("maximumPoolSize");
        minimumIdle = resourceBundle.getString("minimumIdle");
        connectionTimeoutMs = resourceBundle.getString("connectionTimeoutMs");
        driverClassName = resourceBundle.getString("driverClassName");

        HikariDataSource hikariDataSource = new HikariDataSource();
        dataSource = hikariDataSource;

        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);

        hikariDataSource.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        hikariDataSource.setMinimumIdle(Integer.parseInt(minimumIdle));
        hikariDataSource.setConnectionTimeout(Integer.parseInt(connectionTimeoutMs));

        hikariDataSource.setDriverClassName(driverClassName);

    }

    public static Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
