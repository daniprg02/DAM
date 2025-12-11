import java.sql.*;

public class pruebasSQL4 {
    public static void main(String[] args) {
        try {
            // Conexión a la base de datos
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/acceso_datos", "root", "");

            // Parámetro de entrada
            String dep = "1"; // número de departamento

            // Sentencia para llamar al procedimiento
            String sql = "{ call nombre_dep(?, ?, ?) }";

            // Preparar la llamada
            CallableStatement llamada = conexion.prepareCall(sql);

            // Parámetros: IN, OUT, OUT
            llamada.setInt(1, Integer.parseInt(dep));     // IN p_dep
            llamada.registerOutParameter(2, Types.VARCHAR); // OUT p_nombre
            llamada.registerOutParameter(3, Types.VARCHAR); // OUT p_localidad

            // Ejecutar el procedimiento
            llamada.executeUpdate();

            // Recuperar los valores de salida
            String nombre = llamada.getString(2);
            String localidad = llamada.getString(3);

            System.out.printf("Nombre Dep: %s, Localidad: %s%n", nombre, localidad);

            // Cerrar recursos
            llamada.close();
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
