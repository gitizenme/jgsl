/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

import java.io.File;

import jgsl.model.JGSLScript;
import jgsl.parser.JGSL_Parser;
import jgsl.parser.ParseException;

/**
 * Parse the specified script file using the JGSL_Parser and report the status of the parse.
 *
 * @author zenarchitect
 * @version $Id: ScriptParser.java,v 1.7 2005/05/24 17:32:17 zenarchitect Exp $
 */

public class ScriptParser {
    /**
     * @link aggregationByValue
     */
    JGSL_Parser parser;

    /** @link dependency */
    /*# ScriptParserException lnkScriptParserException; */

    /** @link dependency */
    /*# Message lnkMessage; */

    /**
     * @directed
     * @link aggregation
     */
    /*# JGSLScript lnkJGSLScript; */

            public JGSLScript execScript(File scriptFile) throws ScriptParserException {
        parseScript(scriptFile);
        if (parser == null) {
            throw new ScriptParserException("jgsl parser: Execution failed, unable to create script parser.");
        }
        JGSLScript script = parser.getScript();
        script.setScriptName(scriptFile.getName());
        return script;
    }

    public String parseScript(File scriptFile) throws ScriptParserException {
        String result = "jgsl parser:  Reading from file " + scriptFile + " . . .\n";
        double initTime = 0;
        double parseTime = 0;
        double startTime = 0;
        double stopTime = 0;
        try {
            startTime = (double) System.currentTimeMillis();
            parser = new JGSL_Parser(new java.io.FileInputStream(scriptFile));
            stopTime = (double) System.currentTimeMillis();
            initTime = (double) stopTime - startTime;
        } catch (java.io.FileNotFoundException e) {
            if (parser != null) {
                JGSLScript script = parser.getScript();
                script.addError(new ScriptError(result + "\njgsl parser:  File " + scriptFile + " not found.\n"));
            } else {
                throw new ScriptParserException("jgsl parser: Execution failed, unable to create script parser.");
            }
        }
        try {
            startTime = (double) System.currentTimeMillis();
            parser.parseScript();
            stopTime = (double) System.currentTimeMillis();
            parseTime = stopTime - startTime;
            result += "jgsl parser:\n";
            result += "\tparsed " + scriptFile + " in " + (initTime + parseTime) / 1000.0 + " sec\n";
            result += "\tinitialization time = " + initTime / 1000.0 + " sec\n";
            result += "\tparse time = " + parseTime / 1000.0 + " sec\n";
            JGSLScript script = parser.getScript();
            script.addMessage(new ScriptMessage(result));
        } catch (ParseException e) {
            String ex = "jgsl parser:  Reading from file " + scriptFile + " . . .\n"
                    + "jgsl parser:  Encountered errors during parse...\n";
            ex += e.getMessage();
            if (parser != null) {
                JGSLScript script = parser.getScript();
                script.addError(new ScriptError(ex));
            } else {
                throw new ScriptParserException(ex);
            }
        }
        return result;
    }

}

