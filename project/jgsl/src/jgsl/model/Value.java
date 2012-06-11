/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.model;

// TODO - write javadocs
/**
 * @author zenarchitect
 * @version $Id: Value.java,v 1.2 2005/05/16 00:54:19 zenarchitect Exp $
 */
public interface Value {
    /**
     * Get the Java representation of this value
     *
     * @return A String containing the Java representation of this value
     */
    public String getJavaValue();
}
