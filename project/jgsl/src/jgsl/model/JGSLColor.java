/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.model;

import java.awt.Color;

/**
 * Declare an instance of a color type.
 *
 * @author zenarchitect
 * @version $Id: JGSLColor.java,v 1.3 2005/05/21 01:42:07 zenarchitect Exp $
 */
public class JGSLColor implements Type, Value, Argument {
    private String name;
    private Color color = Color.BLACK;

    public JGSLColor(String name, Color color) {
        this.name = name + System.currentTimeMillis();
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Get the java Class meta-data for this type
     *
     * @return The Class mete-data for this type
     */
    public Class getJavaClass() {
        return color.getClass();
    }

    /**
     * Get the Java type as a String
     *
     * @return a String containing the type
     */
    public String getJavaType() {
        return color.getClass().getName();
    }

    /**
     * Get the Java representation of this value
     *
     * @return A String containing the Java representation of this value
     */
    public String getJavaValue() {
        String javaValue = String.format("java.awt.Color %s = new java.awt.Color(%d, %d, %d, %d);\n", name, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        return javaValue;
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
