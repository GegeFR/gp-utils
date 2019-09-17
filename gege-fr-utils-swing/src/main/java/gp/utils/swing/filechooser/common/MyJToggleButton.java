/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.swing.filechooser.common;

import javax.swing.JToggleButton;

/**
 *
 * @author gpasquiers
 */
public class MyJToggleButton extends JToggleButton{
    private Object attachment;

    public void attach(Object object){
        attachment = object;
    }

    public Object attachment(){
        return attachment;
    }
}
