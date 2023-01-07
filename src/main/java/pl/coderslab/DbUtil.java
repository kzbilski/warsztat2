package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,"root","coderslab");
    }
}
