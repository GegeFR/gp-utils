/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.swing.filechooser.common;

import gp.utils.swing.filechooser.FileChooser;
import java.io.File;
import java.io.FileFilter;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author gpasquiers
 */
public class Main {

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            FileChooser controler = new FileChooser(null, true, FileChooser.MODE_OPEN);
            FileFilter png = new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".png");
                }

                @Override
                public String toString(){
                    return "Images (PNG)";
                }
            };

            controler.addDisplayFilter(png);
            controler.selectDisplayFilter(png);
            controler.setAcceptFilter(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().toLowerCase().endsWith(".png");
                }
            });

            System.out.println(controler.show());
            System.out.println(controler.getFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
