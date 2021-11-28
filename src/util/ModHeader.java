/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


public class ModHeader implements TableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent componente = null;
        
        if (value instanceof String) {
            componente = new JLabel((String) value);
            ((JLabel)componente).setHorizontalAlignment(SwingConstants.CENTER);
            ((JLabel)componente).setSize(30,componente.getWidth());
            ((JLabel)componente).setPreferredSize(new Dimension(6, componente.getWidth()));    
        }
        
        componente.setOpaque(true);
        componente.setFont(new Font("Verdana", Font.BOLD, 12)); 
        componente.setBackground(new Color(0,34,57));
        componente.setForeground(new Color(211, 211, 211));
        table.setGridColor(Color.GREEN);
        return componente;
    }
    
}
