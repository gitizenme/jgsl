/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;


import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * FileFilter for .jgsl files
 *
 * @author zenarchitect
 * @version $Id: JGSLFileFilter.java,v 1.5 2005/05/16 00:54:16 zenarchitect Exp $
 */
public class JGSLFileFilter extends FileFilter {
    //Accept all directories and all jgsl files.
            public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = JGSLUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(JGSLUtils.jgsl)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
            public String getDescription() {
        return "JGSL Files (*.jgsl)";
    }
}

class JGSLUtils {
    public final static String jgsl = "jgsl";

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