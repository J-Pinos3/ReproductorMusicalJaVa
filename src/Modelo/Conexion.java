package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection con;
    public Connection getConnection(){
        try{
            String myDB = "jdbc:mysql://localhost:3306/mp3";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(myDB,"root","dBase123");

        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar con la base de datos: " + e.toString());
        }
        return con;


    }//FIN DEL MÉTODO getConnection

}
