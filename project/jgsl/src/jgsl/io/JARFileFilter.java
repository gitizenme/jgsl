/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;


import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * FileFilter for .JAR files
 *
 * @author zenarchitect
 * @version $Id: JARFileFilter.java,v 1.2 2005/05/16 00:54:16 zenarchitect Exp $
 */
public class JARFileFilter extends FileFilter {
    //Accept all directories and all JAR files.
            public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = JGSLUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(JARUtils.jar)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
            public String getDescription() {
        return "JAR Files (*.jar)";
    }
}

class JARUtils {
    public final static String jar = "jar";

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