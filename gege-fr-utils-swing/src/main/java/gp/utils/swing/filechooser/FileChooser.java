/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.swing.filechooser;

import gp.utils.swing.filechooser.bookmarks.Bookmarks;
import gp.utils.swing.filechooser.bookmarks.BookmarksGtkImpl;
import gp.utils.swing.filechooser.files.FilesControler;
import gp.utils.swing.filechooser.path.PathControler;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import gp.utils.swing.filechooser.common.FormatUtils;
import gp.utils.swing.filechooser.places.PlacesControler;
import javax.swing.JOptionPane;

/**
 *
 * @author gpasquiers
 */
public class FileChooser {

    public static final int MODE_OPEN = 0;
    public static final int MODE_SAVE = 1;

    public static final int RESULT_NONE = 0;
    public static final int RESULT_CANCELED = 1;
    public static final int RESULT_SUCEEDED = 2;

    private int result = RESULT_NONE;
    private int mode;

    private FileFilter acceptFilter;
    private File selectedFile;

    private FileChooserView view;

    private PathControler jPanelPathControler;
    private FilesControler jPanelFilesControler;
    private PlacesControler jPanelPlacesControler;


    private Bookmarks bookmarks;

    public FileChooser(Frame parent, boolean modal, int mode_) throws NoSuchFieldException {
        this(parent, modal, mode_, new BookmarksGtkImpl());
    }
    
    public FileChooser(Frame parent, boolean modal, int mode_, Bookmarks bookmarks_) throws NoSuchFieldException {
        view = new FileChooserView(parent, modal);
        mode= mode_;
        bookmarks = bookmarks_;
        selectedFile = null;
        switch(mode){
            case MODE_OPEN:
                view.jPanelSaveName.setVisible(false);
                view.jPanelSave.setVisible(false);
                view.setTitle("Open a file");
                break;
            case MODE_SAVE:
                view.jPanelOpen.setVisible(false);
                view.setTitle("Save to a file");
                break;
        }

        view.jPanelOpen_jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self_Cancel();
            }
        });

        view.jPanelOpen_jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self_Cancel();
            }
        });
        
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                self_Cancel();
            }
        });

        view.jPanelSave_jButtonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self_SelectFile();
            }
        });

        view.jPanelOpen_jButtonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self_SelectFile();
            }
        });
        

        // default accept filter accepts all files (but not directories)
        acceptFilter=new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        };

        // disables the "skin" of the border, make it like an empty panel, cleaner this way
        ((BasicSplitPaneUI)view.jSplitPane.getUI()).getDivider().setBorder(null);

        jPanelPathControler = new PathControler(view.jPanelPathView, new Runnable() {
            public void run() {
                jPanelPathButton_CurrentDirChanged();
            }
        });

        jPanelFilesControler = new FilesControler(view.jPanelFilesView, new Runnable() {
            public void run() {
                jPanelFiles_CurrentDirChanged();
            }
        }, new Runnable() {
            public void run() {
                jPanelFiles_CurrentFileSelected();
            }
        }, new Runnable() {
            public void run() {
                jPanelFiles_CurrentFileClicked();
            }
        });

        jPanelPlacesControler = new PlacesControler(view.jPanelPlacesView, bookmarks, new Runnable() {
            public void run() {
                jPanelPlaces_DirSelected();
            }
        });

        self_SetCurrentDir(new File(System.getProperty("user.home")), true);
    }

    public void setAcceptFilter(FileFilter filter){
        if(null == filter){
            throw new RuntimeException("accept filter cannot be null");
        }
        this.acceptFilter = filter;
    }

    public File getFile(){
        return selectedFile;
    }

    public int show(){
        view.setVisible(true);
        return result;
    }

    public void addDisplayFilter(FileFilter filter){
        jPanelFilesControler.addDisplayFilter(filter);
    }

    public void selectDisplayFilter(FileFilter filter){
        jPanelFilesControler.selectDisplayFilter(filter);
    }

    public void showHiddenFolders(boolean flag){
        jPanelFilesControler.showHiddenFolders(flag);
    }

    private void self_SelectFile(){
        File currentFile = jPanelFilesControler.getSelectedFile();
        File currentDir = jPanelFilesControler.getCurrentDir();

        switch(mode){
            case MODE_OPEN:
                if(null != currentFile){
                    if(acceptFilter.accept(currentFile)){
                        selectedFile = currentFile;
                        result = RESULT_SUCEEDED;
                        view.dispose();
                    }
                    else if(currentFile.isDirectory()){
                        self_SetCurrentDir(currentFile, false);
                    }
                }
                break;
            case MODE_SAVE:
                if(null != currentDir){
                    File candidate = new File(currentDir.getAbsolutePath() + File.separator + view.jPanelSaveName_jTextFieldName.getText());
                    if(acceptFilter.accept(candidate)){
                        selectedFile = candidate;
                        result = RESULT_SUCEEDED;
                        view.dispose();
                    }
                    else if(null != currentFile && currentFile.isDirectory()){
                        self_SetCurrentDir(currentFile, false);
                    }
                }
                break;
        }
    }

    private void self_Cancel(){
        result = RESULT_CANCELED;
        view.dispose();
    }

    private void self_SetCurrentDir(File file, boolean resetPathButtons){
        jPanelPathControler.setCurrentDir(file, resetPathButtons);
        try{
            jPanelFilesControler.setCurrentDir(file);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(view, "Could not list contents of " + file.getAbsolutePath(), "Could not open directory", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jPanelPathButton_CurrentDirChanged() {
        jPanelFilesControler.setCurrentDir(jPanelPathControler.getCurrentDir());
    }


    private void jPanelPlaces_DirSelected(){
        self_SetCurrentDir(jPanelPlacesControler.getSelectedDir(), true);
    }
    

    private void jPanelFiles_CurrentDirChanged(){
        self_SetCurrentDir(jPanelFilesControler.getCurrentDir(), false);
    }
    
    private void jPanelFiles_CurrentFileSelected(){
        if(null != jPanelFilesControler.getSelectedFile()){
            view.jPanelSaveName_jTextFieldName.setText(FormatUtils.formatDate(jPanelFilesControler.getSelectedFile()));
            if(jPanelFilesControler.getSelectedFile().isFile() && !acceptFilter.accept(jPanelFilesControler.getSelectedFile())){
                view.jPanelOpen_jButtonOpen.setEnabled(false);
                view.jPanelSave_jButtonSave.setEnabled(false);
            }
            else{
                view.jPanelOpen_jButtonOpen.setEnabled(true);
                view.jPanelSave_jButtonSave.setEnabled(true);
            }
        }
    }

    private void jPanelFiles_CurrentFileClicked(){
        self_SelectFile();
    }
}
