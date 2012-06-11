/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;


import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * FileFilter for Image files
 *
 * @author zenarchitect
 * @version $Id: ImageFileFilter.java,v 1.1 2005/05/21 01:42:07 zenarchitect Exp $
 */
public class ImageFileFilter extends FileFilter {
    //Accept all directories and all JAR files.
            public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = ImageUtils.getExtension(f);
        if (extension != null) {
            if (extension.equalsIgnoreCase(ImageUtils.jpg) ||
                    extension.equalsIgnoreCase(ImageUtils.png) ||
                    extension.equalsIgnoreCase(ImageUtils.gif) ||
                    extension.equalsIgnoreCase(ImageUtils.bmp)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() {
        return "Image Files (*.jpg, *.png, *.gif, *.bmp)";
    }
}

class ImageUtils {
    public final static String jpg = "jpg";
    public final static String png = "png";
    public final static String gif = "gif";
    public final static String bmp = "bmp";

    /*
     * Get the extension of a file.
     */
            public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}