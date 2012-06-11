/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * A single script error with line and column info.
 *
 * @author zenarchitect
 * @version $Id: ScriptError.java,v 1.2 2005/05/16 00:54:16 zenarchitect Exp $
 */
public class ScriptError implements Message {
    private String message;
    private int lineNumber;
    private int colNumber;

    public ScriptError(String message) {
        this.message = message;
    }


    public ScriptError(String message, int lineNumber, int colNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
        this.colNumber = colNumber;
    }

    /**
     * Returns the type of message
     *
     * @return MessageType
     */
    public MessageType getType() {
        return Message.MessageType.ERROR;
    }

    /**
     * Retuns the message
     *
     * @return A string containing a simple message
     */
    public String getMessage() {
        return "\n>>>>> " + getType() + ": at line " + lineNumber + ", column " + colNumber + "\n\t" + message + "\n>>>>>\n";
    }

    /**
     * Return a detailed message
     *
     * @return A string containing a detailed message
     */
    public String getDetailMessage() {
        return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
    }
}
