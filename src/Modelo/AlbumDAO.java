package Modelo;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class AlbumDAO {
    Conexion cnn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public boolean RegistrarAlbum(Album alb){
        String sql = "INSERT INTO album(nombre_album, foto_album) VALUES(?,?)";

        try{
            con = cnn.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, alb.getNombre());
            ps.setString(2, alb.getRuta());


            int res = ps.executeUpdate();
            if(res > 0){
                JOptionPane.showMessageDialog(null,"Álbum registrada exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"Error al registrar el álbum");
            }

            return true;
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> albumDAO: " + e.toString());
            return false;
        }finally {
            try {
                con.close();
            }catch (SQLException e1){
                System.out.println("Error: albumDAO: " + e1.toString());
            }
        }

    }
    //FIN DEL MÉTODO REGISTRARALBUM



    public List ListarAlbum(){
        List<Album> ListaAlbu = new ArrayList<>();
        String sql = "SELECT * FROM album";
        try{
            con = cnn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();


            while(rs.next()){
                Album al = new Album();
                al.setId(rs.getInt("id_album"));
                al.setNombre(rs.getString("nombre_album"));
                al.setRuta(rs.getString("foto_album"));

                System.out.println(al.getId() + " " + al.getNombre() + " " + al.getRuta() + "\n");
                ListaAlbu.add(al);
            }
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> albumDAO: " + e.toString());
        }
        return ListaAlbu;
    }
    //FIN DEL MÉTODO LISTARALBUM



    public boolean EliminarAlbum(int id){
        String sql = "DELETE FROM album where id_album = ?";
        try{
            con = cnn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> albumDAO: " + e.toString());
            return false;
        }finally {
            try {
                con.close();
            }catch (SQLException e1){
                System.out.println("Error: albumDAO: " + e1.toString());
            }
        }

    }
    //FIN DEL METODO ELIMINARÁLBUM



    public boolean ModificarAlbum(Album alb){
        String sql = "UPDATE album SET nombre_album = ?, foto_album= ? WHERE id_album = ?";

        try{
            con = cnn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, alb.getNombre());
            ps.setString(2, alb.getRuta());
            ps.setInt(3, alb.getId());
            ps.execute();

            return true ;
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> albumDAO: " + e.toString());
            return false;
        }finally {
            try {
                con.close();
            }catch (SQLException e1){
                System.out.println("Error: albumDAO: " + e1.toString());
            }
        }

    }
    //FIN DEL MÉTODO MODIFICAR ALBUM



    public Album BuscarAlbum(String nom){
        Album alb = new Album();
        String sql = "SELECT * FROM album WHERE nombre_album = ?";
        try{
            con = cnn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,nom);
            rs = ps.executeQuery();

            if(rs.next()){
                alb.setId( rs.getInt("id_album") );
                alb.setNombre(rs.getString("nombre_album"));
                alb.setRuta(rs.getString("foto_album"));
            }

        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error -> albumDAO: " + e.toString());
        }

        return  alb;
    }
    //FIN DEL  MÉTODO BUSCAR ALBUM







}///////////////FIN DE LA CLASE ALBUMDAO
