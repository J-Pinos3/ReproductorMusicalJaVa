package Vista;

import Modelo.Cancion;
import Modelo.CancionDAO;
import Modelo.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class vistaCanciones extends JFrame{
    private JTextField txtTitulo;
    private JTextField rutaMp3;
    private JTable tablaSongs;
    private JButton agregarCancionButton;
    private JButton buscarCancionButton;
    private JButton modificarCancionButton;
    private JButton eliminarCancionButton;
    private JButton listarButton;
    private JComboBox cmbAlbum;
    private JTextField rutaFoto;
    private JTextField txtGenero;
    private JTextField txtLanzamiento;
    private JTextField txtArtista;
    private JPanel panelCancionesCrud;

    Cancion can = new Cancion();
    CancionDAO cancionDao = new CancionDAO();

    public vistaCanciones() {

        rellenarAlbumes();

        setContentPane(panelCancionesCrud);
        setVisible(true);
        setTitle("GESTIONAR CANCIONES");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900,700);

        agregarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!"".equals(txtTitulo.getText())){

                }
            }
        });

        /*-------------------------------------------------------------------*/
        eliminarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*-------------------------------------------------------------------*/

        modificarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*-------------------------------------------------------------------*/


        buscarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*-------------------------------------------------------------------*/


        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }//CONSTRUCTOR



    public void rellenarAlbumes(){
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Conexion cnn = new Conexion();
        con = cnn.getConnection();
        String sql = "SELECT nombre_album from album";

        try{
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cmbAlbum.addItem(rs.getString("nombre_album"));
            }

            ps.close();
            rs.close();
        }catch (HeadlessException | SQLException E1){
            JOptionPane.showMessageDialog(null,"No se pudieron cargar los álbumes en el combo box");
        }
    }

}//FIN DE LA CLASE VISTACANCIONES

