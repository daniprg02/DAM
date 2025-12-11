import java.sql.*;
public class Main {
    public static void main(String[] args) {
        try {
// Aunque veáis este código en algunos programas, no hace
//falta cargar el driver ya.
// Class.forName("com.mysql.jdbc.Driver");
// Establecemos la conexion con la BD
            Connection conexion =
                    DriverManager.getConnection("jdbc:mysql://localhost/acceso_datos", "root",
                            "");
// Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM departamentos";
            ResultSet resul = sentencia.executeQuery(sql);
// Recorremos el resultado para visualizar cada fila
// Se hace un bucle mientras haya registros y se vanvisualizando
            while (resul.next()) {
                System.out.printf("%d, %s, %s %n", resul.getInt(1),
                        resul.getString(2), resul.getString(3));
            }
            resul.close(); // Cerrar ResultSet
            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin de main
}// fin de la clase