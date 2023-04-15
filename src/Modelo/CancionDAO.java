package Modelo;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CancionDAO implements Serializable {
    Conexion cnn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public boolean RegistrarCancion(Cancion can){
        String sql = "INSERT INTO cancion(titulo_cancion, autor_cancion, genero_cancion, anio_cancion, song_file, id_album) VALUES(?,?,?,?,?,?)";

        try{
            con = cnn.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, can.getTitulo());
            ps.setString(2, can.getAutor());
            ps.setString(3, can.getGenero());
            ps.setString(4, can.getAnio());
            ps.setString(5, can.getCancionFile());
            ps.setInt(1, can.getAlbumId());

            int res = ps.executeUpdate();
            if(res > 0){
                JOptionPane.showMessageDialog(null,"Cancion registrada exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"Error al registrar la canción");
            }

            return true;
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> cancionDAO: " + e.toString());
            return false;
        }finally {
            try {
                con.close();
            }catch (SQLException e1){
                System.out.println("Error: cancionDAO: " + e1.toString());
            }
        }

    }//FIN DEL MÉTODO REGISTRARCANCION
    public boolean EliminarCancion(String titulo_cancion, String nombre_album){
        String sql= "DELETE FROM cancion WHERE titulo_cancion=? AND id_album=(SELECT id_album FROM album WHERE nombre_album=?)";
        try{
            con = cnn.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, titulo_cancion);
            ps.setString(2, nombre_album);

            int res=ps.executeUpdate();
            if(res > 0){
                JOptionPane.showMessageDialog(null,"Cancion eliminada exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"Error al eliminar la canción");
            }

            return true;
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> cancionDAO: " + e.toString());
            return false;
        }finally {
            try {
                con.close();
            }catch (SQLException e1){
                System.out.println("Error: cancionDAO: " + e1.toString());
            }
        }
    }//FIN DEL MÉTODO ELIMINARCANCION


}
