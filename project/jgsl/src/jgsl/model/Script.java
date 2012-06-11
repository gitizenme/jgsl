/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;


/**
 * The script interface provide the set of operations for a script
 *
 * @author zenarchitect
 * @version $Id: Script.java,v 1.2 2005/05/16 00:54:19 zenarchitect Exp $
 */
public interface Script {
    /**
     * Get the script name
     *
     * @return String containing the script name
     */
    String getScriptName();

    /**
     * Set the scipt name
     *
     * @param scriptName name of the script file
     */
    void setScriptName(String scriptName);

    /**
     * Return the Java implementation of this script
     *
     * @return the Java language implementation of this script
     */
    String getJava();

    /**
     * Returns the JGSL script documentation as specified in the DOC keyword by the script author.
     *
     * @return The script documentation
     */
    String getDocumentation();

}
