/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.model;

// TODO - write javadocs
/**
 * @author zenarchitect
 * @version $Id: JGSLInteger.java,v 1.2 2005/05/16 00:54:18 zenarchitect Exp $
 */
public class JGSLInteger implements Type, Value, Argument {
    private String name;
    private Integer value;

    public JGSLInteger(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public JGSLInteger(String name, String value) {
        this.name = name;
        this.value = Integer.valueOf(value);
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Get the java Class meta-data for this type
     *
     * @return The Class mete-data for this type
     */
    public Class getJavaClass() {
        return value.getClass();
    }

    /**
     * Get the Java type as a String
     *
     * @return a String containing the type
     */
    public String getJavaType() {
        return value.getClass().getName();
    }

    /**
     * Get the Java representation of this value
     *
     * @return A String containing the Java representation of this value
     */
    public String getJavaValue() {
        return value.toString();
    }

    /**
     * Get the name of the argument
     *
     * @return String containing the name
     */
    public String getName() {
        return name;
    }
}
