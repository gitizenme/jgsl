/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * Record a script warning message.
 *
 * @author zenarchitect
 * @version $Id: ScriptWarning.java,v 1.2 2005/05/16 00:54:17 zenarchitect Exp $
 */
public class ScriptWarning implements Message {
    private String message;
    private int lineNumber = -1;
    private int colNumber = -1;

    public ScriptWarning(String message) {
        this.message = message;
    }

    public ScriptWarning(String message, int lineNumber, int colNumber) {
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
        return Message.MessageType.WARNING;
    }

    /**
     * Retuns the message
     *
     * @return A string containing a simple message
     */
    public String getMessage() {
        return ">>>>> " + getType() + ":\n" + message + ">>>>>\n\n";
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
