/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

import java.io.Serializable;

/**
 * A logical statement is one in which the product of a logical comparison resutls in TRUE or FALSE.
 *
 * @author zenarchitect
 * @version $Id: Logical.java,v 1.2 2005/05/16 00:54:18 zenarchitect Exp $
 */
public class Logical implements Statement, Serializable {
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
