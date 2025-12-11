import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class prueba2SQL {
    public static void main(String[] args) {
        try
        {
//Class.forName("com.mysql.jdbc.Driver"); //Cargar eldriver
//Establecer la conexion con la BD
            Connection conexion = DriverManager.getConnection
                    ("jdbc:mysql://localhost/acceso_datos", "root", "");
            DatabaseMetaData dbmd = conexion.getMetaData();//Crear
            //objeto DatabaseMetaData
            ResultSet resul = null;
            System.out.println("CLAVE PRIMARIA TABLA DEPARTAMENTOS:");

            System.out.println("===================================");
            ResultSet pk = dbmd.getPrimaryKeys(null, "acceso_datos",
                    "departamentos");
            String pkDep="", separador="";
            while (pk.next()) {
                pkDep = pkDep + separador +
                        pk.getString("COLUMN_NAME");//getString(4)
                separador="+";
            }
            System.out.println("Clave Primaria: " + pkDep);
            conexion.close(); //Cerrar conexion

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }//fin de main
}