/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.places;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;

/**
 * Custom CListSelectionModel that avoids selection of Separators in JTable
 * @author gpasquiers
 */
public class PlacesListSelectionModel extends DefaultListSelectionModel{
    private JTable jTable;

    public PlacesListSelectionModel(JTable _jTable){
        jTable = _jTable;
    }

    @Override
    public void setSelectionInterval(int index0, int index1) {
        if(jTable.getModel().getValueAt(index0 ,0) instanceof PlacesSeparator){
            return;
        }
        if(jTable.getModel().getValueAt(index1 ,0) instanceof PlacesSeparator){
            return;
        }

        super.setSelectionInterval(index0, index1);
    }

}
