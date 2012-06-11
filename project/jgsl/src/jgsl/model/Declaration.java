/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

import java.io.Serializable;


/**
 * A Declaration statement is one that contains the declaration of a script variable.
 *
 * @author zenarchitect
 * @version $Id: Declaration.java,v 1.2 2005/05/16 00:54:18 zenarchitect Exp $
 */
public class Declaration implements Statement, Serializable {
    private String type;
    private String identifier;
    private String value;

    /**
     * Create a declaration with a given type, identifier and initial value
     *
     * @param type       type of the declaration
     * @param identifier script identifier
     * @param value      initial value
     */
    public Declaration(String type, String identifier, String value) {
        this.type = type;
        this.identifier = identifier;
        this.value = value;
    }

    /**
     * This method returns the Java language equivalent of the JGSL statement.
     *
     * @return Java language statement from the JGSL
     */
    public String getJava() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Set the JGSL statement body
     */
    public void setJGSL(String jgsl) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Return the type of statement. The String form of the class name.
     */
    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
