/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * ParseStatus interface for collecting and reporting errors and error counts.
 *
 * @author zenarchitect
 * @version $Id: ParseStatus.java,v 1.3 2005/05/24 17:32:17 zenarchitect Exp $
 */
public interface ParseStatus {
    /**
     * Add a ScriptError to the parse status
     *
     * @param se
     */
    public void addError(ScriptError se);

    /**
     * Add a ScriptWarning to the parse status
     *
     * @param sw
     */
    public void addWarning(ScriptWarning sw);

    /**
     * Add a ScriptMessage to the parse status
     *
     * @param sm
     */
    public void addMessage(ScriptMessage sm);

    /**
     * Return the error state of the script
     *
     * @return true of the script contains errors or false otherwise
     */
    boolean hasErrors();

    /**
     * Return the warning state of the script
     *
     * @return true of the script contains warnings or false otherwise
     */
    boolean hasWarnings();

    /**
     * Return the message state of the script
     *
     * @return true of the script contains messages or false otherwise
     */
    boolean hasMessages();
}
