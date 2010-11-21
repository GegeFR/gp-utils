/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.files;

import java.awt.Component;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import gp.utils.swing.filechooser.common.FormatUtils;


/**
 *
 * @author gpasquiers
 */
public class FileDateCellRenderer implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setText(FormatUtils.formatDate((File) value));
        label.setLocation(0, 0);
        label.setSize(label.getPreferredSize().width, table.getRowHeight(row));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(label);
        panel.setPreferredSize(label.getPreferredSize());
        if(isSelected){
            panel.setBackground(UIManager.getColor("Table.selectionBackground"));
        }
        else{
            panel.setBackground(UIManager.getColor("Table.background"));
        }

        return panel;
    }

}
