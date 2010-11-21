/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.bookmarks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.util.LinkedList;

/**
 *
 * @author gpasquiers
 */
public class BookmarksGtkImpl implements Bookmarks{
    private URI bookmarksFile;
    private LinkedList<URI> bookmarks;

    public BookmarksGtkImpl(){
        this(new File(System.getProperty("user.home") + File.separator + ".gtk-bookmarks").toURI());
    }

    public BookmarksGtkImpl(URI bookmarkFile_){
        bookmarks = new LinkedList();
        bookmarksFile = bookmarkFile_;
        readBookmarks();
    }

    private void readBookmarks(){
        File gtkBookmarks = new File(bookmarksFile);
        if(gtkBookmarks.exists()){
            FileReader fileReader = null;
            try{
                fileReader = new FileReader(gtkBookmarks);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                do{
                    line = bufferedReader.readLine();
                    if(null != line && line.length() > 0){
                        URI uri = URI.create(line);
                        bookmarks.add(uri);
                    }
                }
                while(line != null);

            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                if(null != fileReader){
                    try{
                        fileReader.close();
                    }
                    catch(Exception e){
                        // ignore
                    }
                }
            }
        }
    }

    private void writeBookmarks(){
        File file = new File(bookmarksFile);
        FileWriter fileWriter = null;

        try{
            fileWriter = new FileWriter(file);
            for(URI uri:bookmarks){
                fileWriter.write(uri.toString());
                fileWriter.write("\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(null != fileWriter){
                    fileWriter.close();
                }
            }
            catch(Exception e){
                // ignore
            }
        }

    }

    public void add(URI uri) {
        if(contains(uri)){
            bookmarks.add(uri);
            writeBookmarks();
        }
    }

    public void remove(URI uri) {
        if(contains(uri)){
            bookmarks.remove(uri);
            writeBookmarks();
        }
    }

    public URI[] list() {
        return this.bookmarks.toArray(new URI[0]);
    }

    public boolean contains(URI uri) {
        return bookmarks.contains(uri);
    }
}
