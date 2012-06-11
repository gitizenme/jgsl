/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

/**
 * Parse a Sting into and int
 *
 * @author zenarchitect
 * @version $Id: ScriptParserUtil.java,v 1.2 2005/05/16 00:54:17 zenarchitect Exp $
 */
public class ScriptParserUtil {
    public static int parseInt(String val) throws ScriptParserException {
        int retVal = 0;
        try {
            Integer rInt = Integer.parseInt(val);
            retVal = rInt.intValue();
        }
        catch (NumberFormatException e) {
            throw new ScriptParserException("Unable to convert " + val + "to a number.");
        }
        return retVal;
    }
}
