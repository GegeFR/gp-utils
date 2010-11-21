/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.bookmarks;

import java.net.URI;

/**
 *
 * @author gpasquiers
 */
public interface Bookmarks {
    public boolean contains(URI uri);
    public void add(URI uri);
    public void remove(URI uri);
    public URI[] list();
}
