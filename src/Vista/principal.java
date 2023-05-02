package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Cancion;
import Modelo.CancionDAO;
import Modelo.Conexion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.List;
import javazoom.jlgui.basicplayer.BasicPlayer;



public class principal extends JFrame{
    private JPanel principal_panel;
    private JTextField buscarCanciónArtistaOTextField;
    private JButton buscarCanciónButton;
    private JButton buscarÁlbumButton;
    private JButton buscarArtistaButton;
    private JButton CANCIONESButton;
    private JButton ALBUMSButton;
    private JTable tblCanciones;
    private JButton DETENERButton;
    private JButton REPRODUCIRButton;
    private JButton REANUDARButton;
    private JButton PAUSARButton;
    private JProgressBar progressBar1;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnio;
    private JTextField txtAlbum;
    private JLabel lblFoto;
    Cancion can = new Cancion();
    CancionDAO cancionDao = new CancionDAO();

    public principal() {
        setContentPane(principal_panel);
        setVisible(true);
        setTitle("MP3 DE LOS GUERREROS C++");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1300,500);
        ListarMusica();

        BasicPlayer bp = new BasicPlayer();

        /*--------------------------------------------------------------------*/

        CANCIONESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaCanciones vsa = new vistaCanciones();
                vsa.setVisible(true);
            }
        });

        /*--------------------------------------------------------------------*/

        ALBUMSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaAlbumes vsa = new vistaAlbumes();
                vsa.setVisible(true);
            }
        });

        /*--------------------------------------------------------------------*/
        REPRODUCIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*--------------------------------------------------------------------*/
        PAUSARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*--------------------------------------------------------------------*/
        REANUDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*--------------------------------------------------------------------*/
        DETENERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*--------------------------------------------------------------------*/


        tblCanciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        /*--------------------------------------------------------------------*/
    }//CONSTRUCTOR

    public String rutaImagen(int id){
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


    public void ListarMusica(){
        List<Cancion> ListaCan = cancionDao.ListarCancion();

        String rutafotico = "";//rutaFotico(0);

        tblCanciones.setDefaultRenderer(Object.class, new Imagens_JTable());
        String[] titulos = {"ID","TÍTULO","AUTOR","GÉNERO","AÑO LANZAMIENTO","MP3 FILE","ALBUM ID","CARÁTULA"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        tblCanciones.setRowHeight(100);

        Object[] obj = new Object[9];

        for(int i = 0; i < ListaCan.size(); i++){
            obj[0] = ListaCan.get(i).getId();
            obj[1] = ListaCan.get(i).getTitulo();
            obj[2] = ListaCan.get(i).getAutor();
            obj[3] = ListaCan.get(i).getGenero();
            obj[4] = ListaCan.get(i).getAnio();
            obj[5] = ListaCan.get(i).getCancionFile();
            obj[6] = ListaCan.get(i).getAlbumId();
            rutafotico = rutaImagen(ListaCan.get(i).getAlbumId());
            JLabel lbl = new JLabel( new ImageIcon(rutafotico) );
            lbl.setSize(80,80);
            obj[7] = lbl;
            modelo.addRow(obj);
        }
        tblCanciones.setModel(modelo);
    }










}
