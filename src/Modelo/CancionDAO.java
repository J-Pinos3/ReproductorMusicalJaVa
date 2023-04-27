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
            ps.setInt(6, can.getAlbumId());

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

            ps.execute();
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


    public boolean ActualizarCancion(Cancion can){
        String sql="UPDATE cancion SET titulo_cancion=?, autor_cancion=?, genero_cancion=?, anio_cancion=?, song_file=? , id_alnum = ? " +
                "WHERE id_cancion = ?";
        try {
            con = cnn.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, can.getTitulo());
            ps.setString(2, can.getAutor());
            ps.setString(3, can.getGenero());
            ps.setString(4, can.getAnio());
            ps.setString(5, can.getCancionFile());
            ps.setInt(6, can.getAlbumId());
            ps.setInt(7,can.getId());
            ps.execute();

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
    }//FIN DEL MÉTODO ACTUALIZARCANCION


    public List ListarCancion(){
        String sql = "SELECT * FROM cancion ";
        List<Cancion> canciones = new ArrayList<>();
        try {
            con = cnn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();


            while (rs.next()) {
                Cancion can = new Cancion();
                can.setId(rs.getInt("id_cancion"));
                can.setTitulo(rs.getString("titulo_cancion"));
                can.setAutor(rs.getString("autor_cancion"));
                can.setGenero(rs.getString("genero_cancion"));
                can.setAnio(rs.getString("anio_cancion"));
                can.setCancionFile(rs.getString("song_file"));
                can.setAlbumId(rs.getInt("id_album"));
                canciones.add(can);
            }
            return canciones;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error -> cancionDAO: " + e.toString());

        }
        return canciones;
    }//FIN DEL MÉTODO LISTARCANCION


}
