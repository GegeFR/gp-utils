/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.common;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author gpasquiers
 */
public class DualFileFilter implements FileFilter{
    private FileFilter filter1 = null;
    private FileFilter filter2 = null;

    synchronized public void setFilter1(FileFilter filter){
        filter1 = filter;
    }

    synchronized public void setFilter2(FileFilter filter){
        filter2 = filter;
    }

    synchronized public boolean accept(File file) {
        if(filter1 != null && !filter1.accept(file)){
            return false;
        }
        if(filter2 != null && !filter2.accept(file)){
            return false;
        }
        return true;
    }
}
