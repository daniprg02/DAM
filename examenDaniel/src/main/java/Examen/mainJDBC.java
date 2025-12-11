package Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class mainJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/basedatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
