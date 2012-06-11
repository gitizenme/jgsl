/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * Report script parsing exceptions.
 *
 * @author Joe Chavez
 * @version $Id: ScriptParserException.java,v 1.2 2005/05/16 00:54:16 zenarchitect Exp $
 */
public class ScriptParserException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *                method.
     */
    public ScriptParserException(String message) {
        super(message);
    }

}

