package Vista;

import Modelo.Cancion;
import Modelo.CancionDAO;
import Modelo.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.List;

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

        ListarCanciones();
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
                ListarCanciones();
            }
        });

        /*-------------------------------------------------------------------*/
        eliminarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccionado = cmbAlbum.getSelectedIndex();
                String albumBuscar = (String)cmbAlbum.getItemAt(seleccionado);
                if( !"".equals(txtTitulo.getText()) && !"".equals(albumBuscar)   ){

                    int pregunta = JOptionPane.showConfirmDialog(null,
                            "Seguro desea eliminar esta canción");

                    if(pregunta == 0){
                        cancionDao.EliminarCancion(txtTitulo.getText(), albumBuscar);
                    }
                    JOptionPane.showMessageDialog(null, "Canción Eliminada");
                }else{
                    JOptionPane.showMessageDialog(null, "Los campos están vacíos");
                }
            }
        });

        /*-------------------------------------------------------------------*/

        modificarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( "".equals(txtIdCancion.getText()) ){
                    JOptionPane.showMessageDialog(null, "Seleccione una fila");
                }else{
                    if(!"".equals(txtTitulo.getText()) && !"".equals(txtArtista.getText()) && !"".equals(txtGenero.getText())
                    && !"".equals(txtLanzamiento.getText()) && !"".equals(rutaMp3.getText()) && !"".equals(id_Album.getText()) ){

                        can.setTitulo(txtTitulo.getText());
                        can.setAutor(txtArtista.getText());
                        can.setGenero(txtGenero.getText());
                        can.setAnio(txtLanzamiento.getText());
                        can.setCancionFile(rutaMp3.getText());
                        can.setAlbumId(Integer.parseInt(id_Album.getText()) );

                        cancionDao.ActualizarCancion(can);

                    }else{
                        JOptionPane.showMessageDialog(null,"Los Campos están vacíos");
                    }
                }
                ListarCanciones();
            }
        });

        /*-------------------------------------------------------------------*/


        buscarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( !"".equals(txtTitulo.getText()) ){
                    String nom = txtTitulo.getText();

                    Cancion aux = cancionDao.BuscarCancion(nom);

                    txtIdCancion.setText(""+aux.getId());
                    txtTitulo.setText(""+aux.getTitulo());
                    txtArtista.setText(""+aux.getAutor());
                    txtGenero.setText(""+aux.getGenero());
                    txtLanzamiento.setText(""+aux.getAnio());
                    rutaMp3.setText(""+aux.getCancionFile());
                    id_Album.setText(""+aux.getAlbumId());

                    String[] titulos = {"ID","TÍTULO","AUTOR","GÉNERO","AÑO LANZAMIENTO","MP3 FILE","ALBUM ID"};
                    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

                    Object[] obj = new Object[8];
                    for(int i = 0; i <= 1; i++){
                        obj[0] = aux.getId();
                        obj[1] = aux.getTitulo();
                        obj[2] = aux.getAutor();
                        obj[3] = aux.getGenero();
                        obj[4] = aux.getAnio();
                        obj[5] = aux.getCancionFile();
                        obj[6] = aux.getAlbumId();

                        modelo.addRow(obj);
                    }

                    tablaSongs.setModel(modelo);
                }else{
                    JOptionPane.showMessageDialog(null,"Los Campos están vacíos");
                }
            }
        });

        /*-------------------------------------------------------------------*/


        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListarCanciones();
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

        /*-------------------------------------------------------------------*/
        tablaSongs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila = tablaSongs.rowAtPoint(e.getPoint());

                txtIdCancion.setText(tablaSongs.getValueAt(fila,0).toString() );
                txtTitulo.setText(tablaSongs.getValueAt(fila,1).toString());
                txtArtista.setText(tablaSongs.getValueAt(fila,2).toString());
                txtGenero.setText(tablaSongs.getValueAt(fila,3).toString());
                txtLanzamiento.setText(tablaSongs.getValueAt(fila,4).toString());
                rutaMp3.setText(tablaSongs.getValueAt(fila,5).toString());
                id_Album.setText(tablaSongs.getValueAt(fila,6).toString());
                //cmbAlbum.setSelectedItem(tablaSongs.getValueAt(fila,0).toString());
                /*

                    obj[0] = ListaCan.get(i).getId();
                    obj[1] = ListaCan.get(i).getTitulo();
                    obj[2] = ListaCan.get(i).getAutor();
                    obj[3] = ListaCan.get(i).getGenero();
                    obj[4] = ListaCan.get(i).getAnio();
                    obj[5] = ListaCan.get(i).getCancionFile();
                    obj[6] = ListaCan.get(i).getAlbumId();
                    rutafotico = rutaFotico(ListaCan.get(i).getAlbumId());
                    JLabel lbl = new JLabel( new ImageIcon(rutafotico) );
                    lbl.setSize(80,80);
                    obj[7] = lbl;
                    modelo.addRow(obj);
                */
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


    public String rutaFotico(int id){
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Conexion cnn = new Conexion();
        con = cnn.getConnection();
        String sql = "SELECT foto_album from album WHERE id_album = ?";
        String ruta = "";

        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();

            while (rs.next()) {
                //cmbAlbum.addItem(rs.getString("nombre_album"));
                ruta = rs.getString("foto_album");
            }

            ps.close();
            rs.close();
        }catch (HeadlessException | SQLException E1){
            JOptionPane.showMessageDialog(null,"No se pudieron cargar los álbumes en el combo box");
        }

        return ruta;
    }


    public void ListarCanciones(){
        List<Cancion> ListaCan = cancionDao.ListarCancion();

        String rutafotico = "";//rutaFotico(0);

        tablaSongs.setDefaultRenderer(Object.class, new Imagens_JTable());
        String[] titulos = {"ID","TÍTULO","AUTOR","GÉNERO","AÑO LANZAMIENTO","MP3 FILE","ALBUM ID","CARÁTULA"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        tablaSongs.setRowHeight(100);

        Object[] obj = new Object[9];

        for(int i = 0; i < ListaCan.size(); i++){
            obj[0] = ListaCan.get(i).getId();
            obj[1] = ListaCan.get(i).getTitulo();
            obj[2] = ListaCan.get(i).getAutor();
            obj[3] = ListaCan.get(i).getGenero();
            obj[4] = ListaCan.get(i).getAnio();
            obj[5] = ListaCan.get(i).getCancionFile();
            obj[6] = ListaCan.get(i).getAlbumId();
            rutafotico = rutaFotico(ListaCan.get(i).getAlbumId());
            JLabel lbl = new JLabel( new ImageIcon(rutafotico) );
            lbl.setSize(80,80);
            obj[7] = lbl;
            modelo.addRow(obj);
        }
        tablaSongs.setModel(modelo);
    }

}//FIN DE LA CLASE VISTACANCIONES

