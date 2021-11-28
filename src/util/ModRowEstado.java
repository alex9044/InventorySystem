package util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ALEX
 */
public class ModRowEstado extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (table.getValueAt(row, column).toString().equals("Inativo") || table.getValueAt(row, column).toString().equals("Pendente")) {
            setBackground(Color.red);
            setForeground(Color.white);
            setHorizontalAlignment(SwingConstants.CENTER);
        }else{
            setBackground(Color.white);
            setForeground(new Color(0,34,57));
            setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected == true) {
                setBackground(new Color(204,204,204));
                setForeground(new Color(0,34,57));
            }
        }
        return this;
    }
}
