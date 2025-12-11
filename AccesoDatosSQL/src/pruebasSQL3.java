import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pruebasSQL3 {
    public static void main(String[] args) {
        try {
            // Class.forName("com.mysql.jdbc.Driver"); // Cargar eldriver
            // Establecer la conexion con la BD
            Connection conexion =
                    DriverManager.getConnection("jdbc:mysql://localhost/acceso_datos", "root", "");
            DatabaseMetaData dbmd = conexion.getMetaData();// Crear
            // objeto DatabaseMetaData
            ResultSet resul = null;
            System.out.println("CLAVES foraneas que referencian a DEPARTAMENTOS:");
            System.out.println("==============================================");
            ResultSet fk = dbmd.getExportedKeys(null, "acceso_datos",
                    "departamentos");
            while (fk.next()) {
                String fk_name = fk.getString("FKCOLUMN_NAME");
                String pk_name = fk.getString("PKCOLUMN_NAME");
                String pk_tablename = fk.getString("PKTABLE_NAME");
                String fk_tablename = fk.getString("FKTABLE_NAME");
                System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
                System.out.printf("Tabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
            }
            conexion.close(); // Cerrar conexion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// fin de main
}
