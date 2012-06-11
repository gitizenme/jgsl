/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A documentation statement is one that contains documentation of the JGSL script as written by the script author.
 *
 * @author zenarchitect
 * @version $Id: Documentation.java,v 1.3 2005/05/16 00:54:18 zenarchitect Exp $
 */
public class Documentation implements Statement, Serializable {
    private ArrayList<String> docs;

    public Documentation() {
        docs = new ArrayList<String>();
    }

    public void addDoc(String doc) {
        docs.add(doc);
    }

    /**
     * This method returns the Java language equivalent of the JGSL statement.
     *
     * @return Java language statement from the JGSL
     */
    public String getJava() {
        StringBuilder docString = new StringBuilder();

        for (String doc : docs) {
            docString.append(doc);
        }

        return docString.toString();
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
        return "Documentation";
    }
}
