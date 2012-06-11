/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * Interface for message types
 *
 * @author ZenArchitect
 * @version $Id: Message.java,v 1.2 2005/05/16 00:54:16 zenarchitect Exp $
 */
public interface Message {
    /**
     * Message types
     */
    public enum MessageType { MESSAGE, WARNING, ERROR }

    ;

    /**
     * Returns the type of message
     *
     * @return MessageType
     */
    public MessageType getType();

    /**
     * Retuns the message
     *
     * @return A string containing a simple message
     */
    public String getMessage();

    /**
     * Return a detailed message
     *
     * @return A string containing a detailed message
     */
    public String getDetailMessage();

}
