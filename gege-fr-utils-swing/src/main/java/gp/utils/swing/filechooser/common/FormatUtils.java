/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.swing.filechooser.common;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author gpasquiers
 */
public class FormatUtils {

    public static String formatFileName(File file) {
        String result;
        if (null != file.getParentFile()) {
            // file is a directory
            result = file.getName();
        }
        else {
            // file is a filesystem root, do nothing
            // gets a nice name but is slow
            //jLabelText.setText(FileSystemView.getFileSystemView().getSystemDisplayName(file));
            result = file.getAbsolutePath();
        }
        return result;
    }

    public static String formatSize(File file) {
        if(file.isDirectory()){
            return "";
        }
        
        double size = file.length();
        String result;

        int unit = 0;

        while (size > 1024){
            size /= 1024;
            unit ++;
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        result = numberFormat.format(size);

        switch (unit){
            case 1:
                result += " KB";
                break;
            case 2:
                result += " MB";
                break;
            case 3:
                result += " GB";
                break;
            case 4:
                result += " TB";
                break;
            default:
                result = file.length() + " bytes";
                break;
        }

        return result;
    }

    public static String formatDate(File file) {
        long timestamp = file.lastModified();
        Date date = new Date(timestamp);

        Calendar today = Calendar.getInstance();
        today.setTime(new Date());

        Calendar lastused = Calendar.getInstance();
        lastused.setTime(date);


        // if is today
        if(lastused.get(Calendar.YEAR) == today.get(Calendar.YEAR) && lastused.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)){
            // return "hour"
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return dateFormat.format(date);
        }

        // if is yesterday
        if(lastused.get(Calendar.YEAR) == today.get(Calendar.YEAR) && lastused.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1){
            // return "yesterday at hour"
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return "Yesterday at " + dateFormat.format(date);
        }

        // if is less than 6 days ago
        if(lastused.get(Calendar.YEAR) == today.get(Calendar.YEAR) && lastused.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 6){
            // return "day"
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            return dateFormat.format(date);
        }

        // return date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
