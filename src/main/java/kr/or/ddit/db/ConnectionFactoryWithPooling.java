package kr.or.ddit.db;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionFactoryWithPooling {
    private static final String BASENAME = "kr.or.ddit.db.DbInfo";
    private static final Object LOCK = new Object();

    private static volatile HikariDataSource dataSource;

    private ConnectionFactoryWithPooling() {
    }

    private static HikariDataSource createDataSource() {
        log.info("======================= Initializing Hikari DataSource =======================");
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASENAME);
        String url = resourceBundle.getString("url");
        String username = resourceBundle.getString("username");
        String password = resourceBundle.getString("password");
        String maximumPoolSize = resourceBundle.getString("maximumPoolSize");
        String minimumIdle = resourceBundle.getString("minimumIdle");
        String connectionTimeoutMs = resourceBundle.getString("connectionTimeoutMs");
        String driverClassName = resourceBundle.getString("driverClassName");

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);

        hikariDataSource.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        hikariDataSource.setMinimumIdle(Integer.parseInt(minimumIdle));
        hikariDataSource.setConnectionTimeout(Integer.parseInt(connectionTimeoutMs));

        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }

    private static HikariDataSource getDataSource() {
        HikariDataSource current = dataSource;
        if (current != null) {
            return current;
        }

        synchronized (LOCK) {
            if (dataSource == null) {
                dataSource = createDataSource();
            }
            return dataSource;
        }
    }

    public static Connection createConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void shutdown() {
        HikariDataSource current = dataSource;
        if (current == null) {
            return;
        }

        synchronized (LOCK) {
            current = dataSource;
            if (current == null) {
                return;
            }

            dataSource = null;
            current.close();
            log.info("======================= Hikari DataSource Closed =======================");
        }
    }
}
