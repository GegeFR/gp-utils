/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.places;

import gp.utils.swing.filechooser.bookmarks.Bookmarks;
import gp.utils.swing.filechooser.common.FileCellRenderer;
import java.io.File;
import java.net.URI;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gpasquiers
 */
public class PlacesControler {
    private PlacesView view;

    private Runnable onDirSelected;
    private Bookmarks bookmarks;
    
    public PlacesControler(PlacesView view_, Bookmarks bookmarks_, Runnable onDirSelected_){
        view = view_;
        bookmarks = bookmarks_;
        onDirSelected = onDirSelected_;
        view.jPanelPlaces_jScrollBar.getViewport().setBackground(UIManager.getColor("Table.background"));
        view.jPanelPlaces_jTablePlaces.setModel(new DefaultTableModel(new Object[]{"Places"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        view.jPanelPlaces_jTablePlaces.getColumn("Places").setCellRenderer(new FileCellRenderer());
        view.jPanelPlaces_jTablePlaces.setSelectionModel(new PlacesListSelectionModel(view.jPanelPlaces_jTablePlaces));
        view.jPanelPlaces_jTablePlaces.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        view.jPanelPlaces_jTablePlaces.getTableHeader().setReorderingAllowed(false);
        view.jPanelPlaces_jTablePlaces.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                jTablePlaces_SelectionChanged(e);
            }
        });


        // add some relevant directories
        jTablePlaces_AddItem(new File(System.getProperty("user.dir")));
        jTablePlaces_AddItem(new File(System.getProperty("user.home")));
        // fill the system roots
        jTablePlaces_AddItem(new PlacesSeparator());
        for (File file : File.listRoots()) {
            jTablePlaces_AddItem(file);
        }

        if (bookmarks.list().length > 0) {
            jTablePlaces_AddItem(new PlacesSeparator());
            for (URI uri: bookmarks.list()) {
                jTablePlaces_AddItem(new File(uri));
            }
        }
    }

    public File getSelectedDir(){
        Object object = view.jPanelPlaces_jTablePlaces.getModel().getValueAt(view.jPanelPlaces_jTablePlaces.getSelectedRow(), 0);
        if (object instanceof File) {
            return (File) object;
        }
        return null;
    }

    private void jTablePlaces_AddItem(Object object) {
        DefaultTableModel model = (DefaultTableModel) view.jPanelPlaces_jTablePlaces.getModel();
        Object[] rowData = new Object[1];
        rowData[0] = object;
        model.addRow(rowData);
    }

    private void jTablePlaces_SelectionChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Object object = view.jPanelPlaces_jTablePlaces.getModel().getValueAt(view.jPanelPlaces_jTablePlaces.getSelectedRow(), 0);
            if (object instanceof File) {
                onDirSelected.run();
            }
        }
    }
}
