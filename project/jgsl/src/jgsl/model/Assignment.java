/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

import java.io.Serializable;

/**
 * An Assignment is a statement in which the value of one attribute is assigned to another via the "=" operator.
 *
 * @author zenarchitect
 * @version $Id: Assignment.java,v 1.2 2005/05/16 00:54:17 zenarchitect Exp $
 */
public class Assignment implements Statement, Serializable {
    /**
     * Left-hand side of assignment
     */
    private String lhs;

    /**
     * Right-hand side of the assignment
     */
    private String rhs;


    /**
     * Constructs an instance with the left-hand side and right-hand side arguments of the assignment statement
     *
     * @param lhs Left-hand side of the assignment
     * @param rhs Right-hand side of the assignment
     */
    public Assignment(String lhs, String rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
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

    public String getLhs() {
        return lhs;
    }

    public void setLhs(String lhs) {
        this.lhs = lhs;
    }

    public String getRhs() {
        return rhs;
    }

    public void setRhs(String rhs) {
        this.rhs = rhs;
    }
}
