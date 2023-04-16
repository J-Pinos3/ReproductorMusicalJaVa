package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.*;
import java.awt.*;

public class Imagens_JTable extends DefaultTableCellRenderer implements TableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if( value instanceof JLabel ){
            JLabel lbl = (JLabel) value;
            return lbl;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
