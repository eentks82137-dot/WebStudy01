package kr.or.ddit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
    static String url;
    static String username;
    static String password;

    static {
        System.out.println("======================= Before All =======================");
        String basename = "kr.or.ddit.db.DbInfo";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(basename);
        url = resourceBundle.getString("url");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
        String driverClassName = resourceBundle.getString("driverClassName");

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
