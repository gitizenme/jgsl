/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A command statement is a JGSL command that performs a graphics operation.
 *
 * @author zenarchitect
 * @version $Id: Command.java,v 1.3 2005/05/16 00:54:17 zenarchitect Exp $
 */
public class Command implements Statement, Serializable {
    private Commands name;
    private ArrayList<Argument> attributes;
    private ArrayList<Argument> parameters;

    public Command(String name) {
        setName(name);
    }

    public Command(String name, ArrayList<Argument> parameters) {
        setName(name);
        this.parameters = parameters;
    }

    public Command(String name, ArrayList<Argument> attributes, ArrayList<Argument> parameters) {
        setName(name);
        this.attributes = attributes;
        this.parameters = parameters;
    }

    public void setName(String name) {
        if (name.equals(Commands.LINE.getName())) {
            this.name = Commands.LINE;
        }
        if (name.equals(Commands.RECTANGLE.getName())) {
            this.name = Commands.RECTANGLE;
        }
        if (name.equals(Commands.SQUARE.getName())) {
            this.name = Commands.SQUARE;
        }
        if (name.equals(Commands.CIRCLE.getName())) {
            this.name = Commands.CIRCLE;
        }
        if (name.equals(Commands.ELIPSE.getName())) {
            this.name = Commands.ELIPSE;
        }
        if (name.equals(Commands.ARC.getName())) {
            this.name = Commands.ARC;
        }
        if (name.equals(Commands.POLYGON.getName())) {
            this.name = Commands.POLYGON;
        }
        if (name.equals(Commands.DRAW.getName())) {
            this.name = Commands.DRAW;
        } else if (name.equals(Commands.CLEAR.getName())) {
            this.name = Commands.CLEAR;
        } else if (name.equals(Commands.WAIT.getName())) {
            this.name = Commands.WAIT;
        } else if (name.equals(Commands.CANVAS.getName())) {
            this.name = Commands.CANVAS;
        } else if (name.equals(Commands.TEXT.getName())) {
            this.name = Commands.TEXT;
        } else if (name.equals(Commands.LOG.getName())) {
            this.name = Commands.LOG;
        } else if (name.equals(Commands.WARNING.getName())) {
            this.name = Commands.WARNING;
        } else if (name.equals(Commands.ERROR.getName())) {
            this.name = Commands.ERROR;
        } else if (name.equals(Commands.DEBUG.getName())) {
            this.name = Commands.DEBUG;
        }
    }

    /**
     * This method returns the Java language equivalent of the JGSL statement.
     *
     * @return Java language statement from the JGSL
     */
    public String getJava() {
        assert(name != null);
        return name.getFormattedCommand(attributes, parameters);
    }

    /**
     * Set the JGSL statement body
     */
    public void setJGSL(String jgsl) {
        //TODO: To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Return the type of statement. The String form of the class name.
     */
    public String getType() {
        assert(name != null);
        return name.getName();
    }
}
