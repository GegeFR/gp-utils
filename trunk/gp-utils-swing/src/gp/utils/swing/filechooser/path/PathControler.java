/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.swing.filechooser.path;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.JToggleButton;
import gp.utils.swing.filechooser.common.FormatUtils;
import gp.utils.swing.filechooser.common.MyJToggleButton;

/**
 *
 * @author gpasquiers
 */
public class PathControler {
    private Vector<MyJToggleButton> buttonsStack;

    private int indexForButtonToDisplay;
    
    private PathView view;

    private Runnable onCurrentDirChangedCallBack;
    
    public PathControler(PathView view_, Runnable onCurrentDirChangedCallBack_) throws NoSuchFieldException {
        view = view_;
        onCurrentDirChangedCallBack = onCurrentDirChangedCallBack_;
        
        buttonsStack = new Vector();
        indexForButtonToDisplay = -1;

        view.jPanelButtons.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                jPanelButtons_Resized(evt);
            }
        });

        view.jButtonLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLeft_Clicked(e);
            }
        });

        view.jButtonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonRight_Clicked(e);
            }
        });

    }

    public void setCurrentDir(File file, boolean resetIndex){
        // populate the buttons stack
        createButtons(file);

        if(resetIndex || indexForButtonToDisplay >= buttonsStack.size()){
            indexForButtonToDisplay = buttonsStack.size() - 1;
        }
        
        displayButtons();

        // make sure buttonsStack.size() - 1 is visible while
        // indexForButtonToDisplay is the lowest possible
        while(buttonsStack.get(buttonsStack.size() - 1).getParent() == null){
            indexForButtonToDisplay++;
            displayButtons();
        }
    }

    public File getCurrentDir(){
        for(MyJToggleButton button:buttonsStack){
            if(button.isSelected()){
                return (File) button.attachment();
            }
        }
        return null;
    }

    private void jPanelButtons_Resized(ComponentEvent evt) {
        displayButtons();
    }

    private void createButtons(File file){
        buttonsStack.clear();
        boolean firstButtonDone = false;
        do{
            MyJToggleButton button = new MyJToggleButton();
            button.attach(file);
            button.setText(FormatUtils.formatFileName(file));
            button.setMargin(new Insets(2, 3, 2, 3));

            if(!firstButtonDone){
                firstButtonDone = true;
                button.setEnabled(false);
                button.setSelected(true);
            }
            
            //button.setFont(button.getFont().deriveFont(Font.ITALIC));

            buttonsStack.insertElementAt(button, 0);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jPanelPathButton_ButtonClicked(e);
                }
            });
            file = file.getParentFile();
        }
        while(file != null);
    }



    private void displayButtons(){
        view.jPanelButtons.setLayout(null);
        view.jPanelButtons.removeAll();
        
        if(buttonsStack.isEmpty()){
            return;
        }

        if(indexForButtonToDisplay == -1){
            indexForButtonToDisplay = 0;
        }

        MyJToggleButton buttonToDisplay = buttonsStack.get(indexForButtonToDisplay);
        
        if(buttonToDisplay.getPreferredSize().width > view.jPanelButtons.getWidth()){
            buttonToDisplay.setSize(view.jPanelButtons.getWidth(), buttonToDisplay.getPreferredSize().height);
        }
        else{
            buttonToDisplay.setSize(buttonToDisplay.getPreferredSize().width, buttonToDisplay.getPreferredSize().height);
        }

        buttonToDisplay.setLocation(0, 0);
        view.jPanelButtons.add(buttonToDisplay);

        //add the buttons to the right
        int indexForOtherButton;
        int xForOtherButton = buttonToDisplay.getSize().width;
        for(indexForOtherButton = indexForButtonToDisplay + 1; indexForOtherButton < buttonsStack.size(); indexForOtherButton++){
            MyJToggleButton otherButton = buttonsStack.get(indexForOtherButton);
            if(xForOtherButton + otherButton.getPreferredSize().width < view.jPanelButtons.getWidth()){
                otherButton.setLocation(xForOtherButton, 0);
                otherButton.setSize(otherButton.getPreferredSize().width, otherButton.getPreferredSize().height);
                view.jPanelButtons.add(otherButton);

                xForOtherButton += otherButton.getSize().width;
            }
            else{
                break;
            }
        }

        // if we could display everything to the right, dont show the right button
        if(indexForOtherButton == buttonsStack.size()){
            view.jButtonRight.setEnabled(false);
        }
        else{
            view.jButtonRight.setEnabled(true);
        }

        // if buttontoDisplay is the index 0, there is no need to display the left button
        if(indexForButtonToDisplay == 0){
            view.jButtonLeft.setEnabled(false);
        }
        else{
            view.jButtonLeft.setEnabled(true);
        }

        view.jPanelButtons.revalidate();
        view.jPanelButtons.repaint();
    }

    private void jPanelPathButton_ButtonClicked(ActionEvent e) {
        MyJToggleButton button = (MyJToggleButton) e.getSource();
        if (button.isSelected()) {
            button.setEnabled(false);

            for (JToggleButton otherButton : buttonsStack) {
                if (!otherButton.equals(button)) {
                    otherButton.setEnabled(true);
                    otherButton.setSelected(false);
                }
            }
            onCurrentDirChangedCallBack.run();
        }
    }

    private void jButtonLeft_Clicked(ActionEvent e){
        if(indexForButtonToDisplay > 0){
            indexForButtonToDisplay--;
        }
        this.displayButtons();
    }

    private void jButtonRight_Clicked(ActionEvent e){
        if(indexForButtonToDisplay < buttonsStack.size() - 1){
            indexForButtonToDisplay++;
        }
        this.displayButtons();
    }
}
