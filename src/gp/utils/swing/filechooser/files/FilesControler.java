/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import gp.utils.swing.filechooser.common.DualFileFilter;
import gp.utils.swing.filechooser.common.FileCellRenderer;
import gp.utils.swing.filechooser.common.FormatUtils;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JViewport;

/**
 *
 * @author gpasquiers
 */
public class FilesControler {
    private FilesView view;
    private DualFileFilter displayFilters = new DualFileFilter();
    private FileFilter hiddenFileFilter;

    private File currentDir;
    private File selectedFile;


    private Runnable onCurrentDirChangedCallBack;
    private Runnable onCurrentFileSelectionCallBack;
    private Runnable onCurrentFileClickCallBack;


    public FilesControler(FilesView view_, Runnable onCurrentDirChangedCallBack_, Runnable onCurrentFileSelectionCallBack_, Runnable onCurrentFileClickCallBack_){
        view = view_;
        onCurrentDirChangedCallBack = onCurrentDirChangedCallBack_;
        onCurrentFileSelectionCallBack = onCurrentFileSelectionCallBack_;
        onCurrentFileClickCallBack = onCurrentFileClickCallBack_;
        
        view.jScrollPaneFiles.getViewport().setBackground(UIManager.getColor("Table.background"));

        // filter in order not to display hidden files and enable it by default
        hiddenFileFilter = new FileFilter() {
            public boolean accept(File file) {
                return !(file.isHidden() || file.getName().startsWith("."));
            }
        };
        displayFilters.setFilter1(hiddenFileFilter);


        view.jComboBoxCustomFilters.removeAllItems();
        view.jComboBoxCustomFilters.addItem(new FileFilter() {
            public boolean accept(File file) {
                return true;
            }

            @Override
            public String toString(){
                return "All files";
            }
        });
        view.jComboBoxCustomFilters.setSelectedIndex(0);
        view.jComboBoxCustomFilters.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    displayFilters.setFilter2((FileFilter) e.getItem());
                    jTableFiles_Refresh();
                }
            }
        });

        displayFilters.setFilter2((FileFilter) view.jComboBoxCustomFilters.getItemAt(0));
        
        jTableFiles_Init();
        jTextFieldFilter_Init();
        jPopupMenu_Init();
    }


    public File getSelectedFile(){
        return selectedFile;
    }

    public File getCurrentDir(){
        return currentDir;
    }

    public void setCurrentDir(File dir){
        jTableFiles_SetCurrentDir(dir);
    }
    
    public void addDisplayFilter(FileFilter filter){
        view.jComboBoxCustomFilters.addItem(filter);
    }

    public void selectDisplayFilter(FileFilter filter){
        view.jComboBoxCustomFilters.setSelectedItem(filter);
    }

    public void showHiddenFolders(boolean flag){
        view.jPopupMenuFiles_HiddenFilesItem.setSelected(flag);
        if(flag){
            displayFilters.setFilter1(null);
        }
        else{
            displayFilters.setFilter1(hiddenFileFilter);
        }
    }


    private void jPopupMenu_Init() {
        view.jScrollPaneFiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    view.jPopupMenuFiles.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        view.jPopupMenuFiles.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e){}
            public void popupMenuWillBecomeVisible(PopupMenuEvent e){}
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (view.jPopupMenuFiles_HiddenFilesItem.isSelected()) displayFilters.setFilter1(hiddenFileFilter);
                else displayFilters.setFilter1(null);
                jTableFiles_Refresh();
            }
        });

        view.jTableFiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    view.jPopupMenuFiles.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        view.jPopupMenuFiles.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e){}
            public void popupMenuWillBecomeVisible(PopupMenuEvent e){}
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (view.jPopupMenuFiles_HiddenFilesItem.isSelected()) displayFilters.setFilter1(hiddenFileFilter);
                else displayFilters.setFilter1(null);
                jTableFiles_Refresh();
            }
        });
    }

    private void jTextFieldFilter_Init() {
        view.jTextFieldFilter.setVisible(false);
        view.jTextFieldFilter.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                view.jComboBoxCustomFilters.setVisible(false);
            }

            public void focusLost(FocusEvent e) {
                view.jTextFieldFilter.setVisible(false);
                view.jComboBoxCustomFilters.setVisible(true);
            }
        });
        view.jTextFieldFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCurrentFileClickCallBack.run();
            }
        });
        view.jTextFieldFilter.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_ESCAPE:
                        view.jTableFiles.requestFocus();

                }
            }
        });
        view.jTextFieldFilter.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                jTableFiles_FindFile(view.jTextFieldFilter.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                jTableFiles_FindFile(view.jTextFieldFilter.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                jTableFiles_FindFile(view.jTextFieldFilter.getText());
            }
        });
    }

    private void jTableFiles_Init() {
        view.jTableFiles.addKeyListener(new  KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                jTableFiles_KeyPressed(e);
            }
        });
        view.jTableFiles.setModel(new DefaultTableModel(new Object[]{"Name", "Size", "Last modified"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(0)).setCellRenderer(new FileCellRenderer());
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(1)).setCellRenderer(new FileLengthCellRenderer());
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(2)).setCellRenderer(new FileDateCellRenderer());

        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(1)).setWidth(55);
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(1)).setMaxWidth(55);
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(1)).setMinWidth(55);

        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(2)).setWidth(100);
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(2)).setMaxWidth(100);
        view.jTableFiles.getColumn(view.jTableFiles.getColumnName(2)).setMinWidth(100);

        view.jTableFiles.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        view.jTableFiles.getTableHeader().setReorderingAllowed(false);
        view.jTableFiles.setRowHeight(22);
        TableRowSorter sorter = new TableRowSorter<DefaultTableModel>((DefaultTableModel) view.jTableFiles.getModel());
        sorter.setComparator(0, new Comparator() {
            public int compare(Object o1, Object o2) {
                File f1 = (File) o1;
                File f2 = (File) o2;
                if (f1.isDirectory() && f2.isFile()) return -1;
                else if (f1.isFile() && f2.isDirectory()) return 1;
                else return f1.compareTo(f2);
            }
        });

        sorter.setComparator(1, new Comparator() {
            public int compare(Object o1, Object o2) {
                File f1 = (File) o1;
                File f2 = (File) o2;
                if (f1.isDirectory() && f2.isFile()) return -1;
                else if (f1.isFile() && f2.isDirectory()) return 1;
                else return Math.round(Math.signum(f1.length() - f2.length()));
            }
        });

        sorter.setComparator(2, new Comparator() {
            public int compare(Object o1, Object o2) {
                File f1 = (File) o1;
                File f2 = (File) o2;
                return Math.round(Math.signum(f1.lastModified() - f2.lastModified()));
            }
        });
        view.jTableFiles.setRowSorter(sorter);
        view.jTableFiles.getRowSorter().toggleSortOrder(0);
        view.jTableFiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    jTableFiles_DoubleClick(e);
                }
            }
        });
        view.jTableFiles.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                jTableFiles_SelectionChanged(e);
            }
        });
    }

    public void scrollToVisible(int rowIndex) {
        if (!(view.jTableFiles.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)view.jTableFiles.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = view.jTableFiles.getCellRect(rowIndex, 0, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
    }

    private void jTableFiles_KeyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            onCurrentFileClickCallBack.run();
            e.consume();
        }
        else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if(null != currentDir.getParentFile()){
                currentDir = currentDir.getParentFile();
                jTableFiles_SetCurrentDir(currentDir);
            }
            e.consume();
        }
        else if (e.getKeyChar() > 31 && e.getKeyChar() < 127){
            view.jTextFieldFilter.setVisible(true);
            view.jComboBoxCustomFilters.setVisible(false);
            view.jTextFieldFilter.setText(Character.toString(e.getKeyChar()));
            view.jTextFieldFilter.requestFocus();
            e.consume();
        }
    }


    private void jTableFiles_FindFile(String nameToFind){
        int count = view.jTableFiles.getModel().getRowCount();
        for(int i =0; i<count; i++){
            File file = (File) view.jTableFiles.getModel().getValueAt(i, 0);
            String name = FormatUtils.formatFileName(file);
            if(name.toLowerCase().startsWith(nameToFind.toLowerCase())){
                int row = view.jTableFiles.convertRowIndexToView(i);
                view.jTableFiles.getSelectionModel().setSelectionInterval(row, row);
                scrollToVisible(row);
                return;
            }
        }
        for(int i =0; i<count; i++){
            File file = (File) view.jTableFiles.getModel().getValueAt(i, 0);
            String name = FormatUtils.formatFileName(file);
            if(name.toLowerCase().contains(nameToFind.toLowerCase())){
                int row = view.jTableFiles.convertRowIndexToView(i);
                view.jTableFiles.getSelectionModel().setSelectionInterval(row, row);
                scrollToVisible(row);
                return;
            }
        }
    }


    private void jTableFiles_DoubleClick(MouseEvent e) {
        int selectedRow = view.jTableFiles.getSelectedRow();
        if (selectedRow != -1) {
            selectedFile = (File) view.jTableFiles.getRowSorter().getModel().getValueAt(view.jTableFiles.convertRowIndexToModel(selectedRow), 0);
            onCurrentFileClickCallBack.run();
        }
    }

    private void jTableFiles_SelectionChanged(ListSelectionEvent e) {
        selectedFile = null;
        if (!e.getValueIsAdjusting()) {
            int selectedRow = view.jTableFiles.getSelectedRow();
            if (selectedRow != -1) {
                selectedFile = (File) view.jTableFiles.getRowSorter().getModel().getValueAt(view.jTableFiles.convertRowIndexToModel(selectedRow), 0);
                onCurrentFileSelectionCallBack.run();
            }
        }
    }

    private void jTableFiles_Refresh(){
        jTableFiles_SetCurrentDir(currentDir);
    }

    private void jTableFiles_SetCurrentDir(File file) {
        if(file != null){
            DefaultTableModel model = (DefaultTableModel) view.jTableFiles.getModel();

            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            File oldDir = currentDir;
            currentDir = file;


            File[] list = file.listFiles();
            if (null != list) {
                for (File aFile : file.listFiles(displayFilters)) {
                    Object[] rowData = new Object[3];
                    rowData[0] = aFile;
                    rowData[1] = aFile;
                    rowData[2] = aFile;
                    model.addRow(rowData);
                }
                
                view.jTableFiles.requestFocus();

                if(!currentDir.equals(oldDir)){
                    onCurrentDirChangedCallBack.run();
                }
            }
            else {
                throw new RuntimeException();
            }
        }
    }
}
