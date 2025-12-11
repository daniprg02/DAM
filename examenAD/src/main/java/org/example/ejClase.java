package org.example;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ejClase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String URL = "jdbc:mysql://localhost:3306/examen";
        String USER = "root";
        String PASSWORD = "";

        try (Statement st = DriverManager.getConnection(URL, USER, PASSWORD).createStatement()) {

            String consulta = "SELECT * FROM Producto";

            ResultSet rs = st.executeQuery(consulta);

            System.out.println("\n"+ "           TABLA DE PRODUCTOS" );
            System.out.println("=============================================");
            while (rs.next()) {
                int id =  rs.getInt("Id");
                String nombre = rs.getString("Nombre");
                float precio = rs.getFloat("Precio");

                System.out.println("ID: "+id + " - Nombre: "+nombre + " - Precio: "+precio);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
