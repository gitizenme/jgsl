/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.view;

/**
 * Interface for JGSL script viewer windows.
 *
 * @author zenarchitect
 * @version $Id: ScriptViewer.java,v 1.3 2005/05/21 01:42:12 zenarchitect Exp $
 */

public interface ScriptViewer {
    /**
     * Render the script code in fullClassName to a GUI window.
     *
     * @param fullClassName
     */
    void renderScript(String fullClassName, String saveToFileType);

}
