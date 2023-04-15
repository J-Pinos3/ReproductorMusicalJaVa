package Vista;
import Modelo.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class vistaAlbumes extends JFrame{
    private JTextField txtNomAlbum;
    private JTextField txtRutaAlbum;
    private JButton ARCHIVOSButton;
    private JTable tblAlbum;
    private JButton agregarAlbumButton;
    private JButton buscarAlbumButton;
    private JButton modificarAlbumButton;
    private JButton eliminarAlbumButton;
    private JButton listarButton;
    private JPanel panelAlbum;
    private JTextField txtIdAlbum;

    Album alb = new Album();
    AlbumDAO albumDao = new AlbumDAO();

    public vistaAlbumes() {

        setContentPane(panelAlbum);
        setVisible(true);
        setTitle("GESTIONAR ÁLBUMES");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900,700);

        /*--------------------------------------------------------------------*/
        agregarAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( !"".equals(txtNomAlbum.getText()) && !"".equals(txtRutaAlbum.getText()) ){
                    alb.setNombre(txtNomAlbum.getText());
                    alb.setRuta(txtRutaAlbum.getText());

                    albumDao.RegistrarAlbum(alb);
                }else{
                    JOptionPane.showMessageDialog(null,"Los campos están vacíos");
                }
            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        tblAlbum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila = tblAlbum.rowAtPoint(e.getPoint());
                txtIdAlbum.setText(tblAlbum.getValueAt(fila,0).toString());
                txtNomAlbum.setText(tblAlbum.getValueAt(fila,1).toString());
                txtRutaAlbum.setText(tblAlbum.getValueAt(fila,2).toString());
            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListarAlbumes();
            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        eliminarAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!"".equals(txtIdAlbum.getText())){
                    int pregunta = JOptionPane.showConfirmDialog(null,
                     "Seguro desea eliminar este álbum?");

                    if (pregunta == 0) {
                        int id = Integer.parseInt(txtIdAlbum.getText());
                        albumDao.EliminarAlbum(id);
                    }
                    JOptionPane.showMessageDialog(null,"Álbum eliminado");
                }else{
                    JOptionPane.showMessageDialog(null,"Los Campos están vacíos");
                }
                ListarAlbumes();
            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        modificarAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( "".equals(txtIdAlbum.getText()) ){
                    JOptionPane.showMessageDialog(null, "Seleccione una fila");
                }else{
                    if( !"".equals(txtIdAlbum.getText()) && !"".equals(txtNomAlbum.getText()) && !"".equals(txtRutaAlbum.getText()) ){
                        alb.setNombre(txtNomAlbum.getText());
                        alb.setRuta(txtRutaAlbum.getText());
                        alb.setId( Integer.parseInt(txtIdAlbum.getText()) );

                        albumDao.ModificarAlbum(alb);
                        ListarAlbumes();
                    }else{
                        JOptionPane.showMessageDialog(null,"Los Campos están vacíos");
                    }
                }
            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        buscarAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( !"".equals(txtNomAlbum.getText()) ){
                    String nom = txtNomAlbum.getText();

                    Album aux = albumDao.BuscarAlbum(nom);
                    txtIdAlbum.setText(""+aux.getId());
                    txtNomAlbum.setText(""+aux.getNombre());
                    txtRutaAlbum.setText(""+aux.getRuta());

                    String[] titulos = {"ID","Nombre","Carátula"};
                    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

                    Object[] obj = new Object[3];
                    //               < 1 porque solo debería haber un album, no se repite
                    for(int i = 0; i < 1; i++){
                        obj[0] = aux.getId();
                        obj[1] = aux.getNombre();
                        obj[2] = aux.getRuta();

                        modelo.addRow(obj);
                    }
                    tblAlbum.setModel(modelo);
                }else{
                    JOptionPane.showMessageDialog(null,"Los Campos están vacíos");
                }

            }
        });
        /*--------------------------------------------------------------------*/


        /*--------------------------------------------------------------------*/
        ARCHIVOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jchooser = new JFileChooser();
                FileNameExtensionFilter filtrado = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg","png","gif") ;
                jchooser.setFileFilter(filtrado);
                String rutaImg = "";

                int respuesta = jchooser.showOpenDialog(panelAlbum);//cancelar o aceptar
                if(respuesta == JFileChooser.APPROVE_OPTION){
                    rutaImg = jchooser.getSelectedFile().getPath();
                    txtRutaAlbum.setText(rutaImg);
                }
            }
        });
        /*--------------------------------------------------------------------*/
    }//CONSTRUCTOR


    public void ListarAlbumes(){
        List<Album> ListaALb = albumDao.ListarAlbum();
        String[] titulos = {"ID","Nombre","Carátula"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        Object[] obj = new Object[4];
        for(int i = 0; i < ListaALb.size(); i++){
            obj[0] = ListaALb.get(i).getId();
            obj[1] = ListaALb.get(i).getNombre();
            obj[2] = ListaALb.get(i).getRuta();

            modelo.addRow(obj);
        }
        tblAlbum.setModel(modelo);
    }
    //FIN DEL MÉTODO LISTAR ALBUMES



}//FIN DE LA CLASE VISTAALBUMES


