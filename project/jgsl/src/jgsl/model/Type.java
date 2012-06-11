/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.model;

// TODO - write javadocs
/**
 * @author zenarchitect
 * @version $Id: Type.java,v 1.2 2005/05/16 00:54:19 zenarchitect Exp $
 */
public interface Type {
    /**
     * Get the java Class meta-data for this type
     *
     * @return The Class mete-data for this type
     */
    public Class getJavaClass();

    /**
     * Get the Java type as a String
     *
     * @return a String containing the type
     */
    public String getJavaType();

}
