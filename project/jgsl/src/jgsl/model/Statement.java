/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

/**
 * The Statement interface provides the set of operations common to all JGSL statements.
 *
 * @author zenarchitect
 * @version $Id: Statement.java,v 1.2 2005/05/16 00:54:19 zenarchitect Exp $
 */
public interface Statement {
    /**
     * This method returns the Java language equivalent of the JGSL statement.
     *
     * @return Java language statement from the JGSL
     */
    String getJava();

    /**
     * Set the JGSL statement body
     */
    void setJGSL(String jgsl);

    /**
     * Return the type of statement. The String form of the class name.
     */
    String getType();
}
