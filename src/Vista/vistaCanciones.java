package Vista;

import Modelo.Cancion;
import Modelo.CancionDAO;
import Modelo.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JLabel id_Album;
    private JTextField txtIdCancion;
    private JButton archivosButton;

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

        /*---------------------------------------------------------------*/

        agregarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selec = cmbAlbum.getSelectedIndex();

                if( !"".equals(txtTitulo.getText()) && !"".equals(txtArtista.getText()) && !"".equals(txtGenero.getText())
                    && !"".equals(txtLanzamiento.getText()) && !"".equals(rutaMp3.getText()) && !"".equals(id_Album.getText()) ){

                    can.setTitulo(txtTitulo.getText());
                    can.setAutor(txtArtista.getText());
                    can.setGenero(txtGenero.getText());
                    can.setAnio(txtLanzamiento.getText());
                    can.setCancionFile(rutaMp3.getText());
                    can.setAlbumId(Integer.parseInt(id_Album.getText()) );

                    cancionDao.RegistrarCancion(can);
                }else{
                    JOptionPane.showMessageDialog(null,"Los campos están vacíos");
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

        /*-------------------------------------------------------------------*/
        cmbAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int seleccionado = cmbAlbum.getSelectedIndex();
                String albumBuscar = (String)cmbAlbum.getItemAt(seleccionado);
                Connection con;
                PreparedStatement ps;
                ResultSet rs;
                Conexion cnn = new Conexion();
                con = cnn.getConnection();
                //RELLENAR LOS CAMPOS DEL ALBUM, CARÁTULA E ID
                String sql = "SELECT id_album, nombre_album, foto_album from album " +
                        "WHERE nombre_album = ?";

                try{
                    ps=con.prepareStatement(sql);
                    ps.setString(1,albumBuscar);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        //cmbAlbum.addItem(rs.getString("nombre_album"));
                        //cmbAlbum.setSelectedItem(rs.getString("nombre_album"));
                        rutaFoto.setText(rs.getString("foto_album"));
                        id_Album.setText(rs.getString("id_album"));
                    }

                    ps.close();
                    rs.close();
                }catch (HeadlessException | SQLException E1){
                    JOptionPane.showMessageDialog(null,"No se pudieron cargar los álbumes del combo box");
                }
            }
        });

        /*-------------------------------------------------------------------*/
        archivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jchooser = new JFileChooser();
                FileNameExtensionFilter filtrado = new FileNameExtensionFilter("MP3", "mp3") ;
                jchooser.setFileFilter(filtrado);
                String rutaImg = "";

                int respuesta = jchooser.showOpenDialog(panelCancionesCrud);//cancelar o aceptar
                if(respuesta == JFileChooser.APPROVE_OPTION){
                    rutaImg = jchooser.getSelectedFile().getPath();
                    rutaMp3.setText(rutaImg);
                }
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

