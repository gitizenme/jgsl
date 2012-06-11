/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.controller.script;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;

import jgsl.io.ScriptParser;
import jgsl.io.ScriptParserException;
import jgsl.model.JGSLScript;
import jgsl.util.JarPackager;
import jgsl.util.JarPackagerException;
import jgsl.view.swing.JGSLSwingFrame;
import jgsl.view.swing.SwingScriptViewer;

/**
 * The ScriptEngine class is the controller for the JGSL application. It contains the command line processor for the
 * command console interface. The *Interactive methods are the controller interfaced for the Interactive GUI.
 *
 * @author zenarchitect
 * @version $Id: ScriptEngine.java,v 1.8 2005/05/21 01:42:06 zenarchitect Exp $
 */
public class ScriptEngine {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    // create Options object
    private Options options = new Options();

    /** @link dependency */
    /*# JGSLScript lnkJGSLScript; */

    /** @link dependency */
    /*# ScriptEngineException lnkScriptEngineException; */

    /** @link aggregationByValue
    * @directed*/
    /*# ScriptParser lnkScriptParser; */

    /** @link aggregationByValue
    * @directed*/
    /*# SwingScriptViewer lnkSwingScriptViewer; */

    /** @directed
    * @link aggregation*/
    /*# JGSLScript lnkJGSLScript1; */

    /**
     * Parse the script supplied in fileName and return the JGSLScript containing the JGSL object model for the script.
     *
     * @param fileName Name of the JGSL script file
     * @return JGSLScript object containing the script object model
     * @throws ScriptParserException If a problem is encountered during parsing a ScriptParser exception will be
     *                               thrown.
     * @see jgsl.model.JGSLScript
     */
    public JGSLScript parseInteractive(File fileName) throws ScriptParserException {
        ScriptParser sp = new ScriptParser();
        JGSLScript script = sp.execScript(fileName);
        return script;
    }

    /**
     * This method will parse the script contained in fileName and then display the result in the JGSL viewer.
     *
     * @param fileName Name of the JGSL script file
     * @return JGSLScript object containing the script object model
     * @throws ScriptParserException If a problem is encountered during parsing a ScriptParser exception will be
     *                               thrown.
     * @see jgsl.model.JGSLScript
     */
    public JGSLScript viewInteractive(File fileName, String saveToFileName) throws ScriptParserException {
        JGSLScript script = parseInteractive(fileName);
        String fullClassName = script.generateImplementation();
        if (fullClassName != null) {
            SwingScriptViewer ssv = new SwingScriptViewer();
            ssv.renderScript(fullClassName, saveToFileName);
        } else {
            sysLogger.debug("Unable to generated implementation, full class name for file: " + fileName.getAbsolutePath());
            throw new ScriptParserException("Unable to generated implementation, full class name for file: " + fileName.getAbsolutePath());
        }
        return script;
    }

    /**
     * The method will parse the script contained in scriptFileName and then create an executable JAR file with name of
     * jarFileName containing the Java class for the JGSL script.
     *
     * @param scriptFileName Name of JGSL script to create the JAR from.
     * @param jarFileName    Name of JAR file to generate
     * @return JGSLScript object containing the script object model
     * @throws ScriptParserException If a problem is encountered during parsing a ScriptParser exception will be
     *                               thrown.
     * @throws ScriptEngineException If a problem occurs during the creation of the JAR file.
     * @see jgsl.model.JGSLScript
     */
    public JGSLScript jarInteractive(File scriptFileName, File jarFileName) throws ScriptEngineException, ScriptParserException {
        JGSLScript script = parseInteractive(scriptFileName);
        script.generateImplementation();
        try {
            JarPackager.makeJar(jarFileName, script.getClassFileName(), script.getFullClassName());
        } catch (JarPackagerException e) {
            sysLogger.error(e.getMessage(), e);
            throw new ScriptEngineException("Unable to generated JAR: " + scriptFileName.getAbsolutePath());
        }
        return script;
    }

    /**
     * Process the command line arguments and perform the requested actions. The set of available options is listed
     * below.
     * <p/>
     * <pre>
     * usage: jgsl.JGSL
     *      -d,--doc jgsl script filt script doc file   Generate script documenation
     *      -e,--exec script file                       Execute the script file
     *      -h,--help                                   Print this message
     *      -j,--jar jgsl script file JAR file          Generate JAR file for script
     *      -l,--logLevel user log level                Set user logging level
     *                                                      to one of: LOG, DEBUG, ERROR, WARNING
     *      -p,--parse script file                      Parse the script file and print the results
     *      -s,--sysLogLevel system log level           Set system logging
     *                                                      level to one of: LOG, DEBUG, ERROR, WARNIN
     *      -v,--view Type of viewer, supports: swing   Parse, execute and view the script
     * </pre>
     *
     * @param args Array of String references to valid arguments
     * @throws ScriptParserException If parsing is requested and problem is found this script this exception will be
     *                               thrown.
     * @throws ScriptEngineException If execution or JAR is requested and a problem occurs this exception will be
     *                               thrown.
     */
    public void processCommandLine(String[] args) throws ScriptParserException, ScriptEngineException {
        ResourceBundle res = ResourceBundle.getBundle("jgsl.resources.JGSL");

        sysLogger.info("JGSL arguments: " + Arrays.toString(args));

        // create the command line parser
        CommandLineParser parser = new PosixParser();

        // create the Options

        // help option
        Option help = new Option(res.getString("app.option.help.short"), res.getString("app.option.help.long"), false, res.getString("app.option.help.message"));
        options.addOption(help);

        // log level script option
        Option logLevel = new Option(res.getString("app.option.loglevel.short"), res.getString("app.option.loglevel.long"), true, res.getString("app.option.loglevel.message"));
        logLevel.setArgName(res.getString("app.option.loglevel.level"));
        options.addOption(logLevel);

        // log level script option
        Option sysLogLevel = new Option(res.getString("app.option.sysloglevel.short"), res.getString("app.option.sysloglevel.long"), true, res.getString("app.option.sysloglevel.message"));
        sysLogLevel.setArgName(res.getString("app.option.sysloglevel.level"));
        options.addOption(sysLogLevel);

        // parse script option
        Option parseScript = new Option(res.getString("app.option.parsescript.short"), res.getString("app.option.parsescript.long"), true, res.getString("app.option.parsescript.message"));
        parseScript.setArgName(res.getString("app.option.parsescript.filearg"));
        options.addOption(parseScript);

        // execute script option
        Option execScript = new Option(res.getString("app.option.execscript.short"), res.getString("app.option.execscript.long"), true, res.getString("app.option.execscript.message"));
        execScript.setArgName(res.getString("app.option.execscript.filearg"));
        options.addOption(execScript);

        // view script option
        Option viewScript = new Option(res.getString("app.option.viewscript.short"), res.getString("app.option.viewscript.long"), true, res.getString("app.option.viewscript.message"));
        viewScript.setArgName(res.getString("app.option.viewscript.viewertype"));
        viewScript.setOptionalArg(true);
        options.addOption(viewScript);

        // save to file
        Option saveToFile = new Option(res.getString("app.option.savetofiletype.short"), res.getString("app.option.savetofiletype.long"), true, res.getString("app.option.savetofiletype.message"));
        saveToFile.setArgName(res.getString("app.option.savetofiletype.type"));
        options.addOption(saveToFile);

        // generate docs script option
        Option genDoc = new Option(res.getString("app.option.gendoc.short"), res.getString("app.option.gendoc.long"), true, res.getString("app.option.gendoc.message"));
        genDoc.setArgs(2);
        genDoc.setArgName(res.getString("app.option.gendoc.files"));
        options.addOption(genDoc);

        // generate JAR option
        Option genJar = new Option(res.getString("app.option.genjar.short"), res.getString("app.option.genjar.long"), true, res.getString("app.option.genjar.message"));
        genJar.setArgs(2);
        genJar.setArgName(res.getString("app.option.genjar.files"));
        options.addOption(genJar);

        String saveToFileName = null;
        // parse the command line arguments
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            throw new ScriptEngineException(new StringBuffer().append("app.exception.program.args\n").append(e.getMessage()).toString());
        }

        if (line.hasOption(help.getOpt())) {
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(res.getString("app.command.line.name"), options);
            return;
        }

        if (line.hasOption(logLevel.getOpt())) {
            String level = line.getOptionValue(logLevel.getOpt());
            jgslLogger.setLevel(Level.toLevel(level));
        }

        if (line.hasOption(sysLogLevel.getOpt())) {
            String level = line.getOptionValue(sysLogLevel.getOpt());
            sysLogger.setLevel(Level.toLevel(level));
        }

        if (line.hasOption(parseScript.getOpt())) {
            ScriptParser sp = new ScriptParser();
            String fileName = line.getOptionValue(parseScript.getOpt());
            String result = sp.parseScript(new File(fileName));
            jgslLogger.info(result);
            return;
        }

        if (line.hasOption(execScript.getOpt())) {
            ScriptParser sp = new ScriptParser();
            String fileName = line.getOptionValue(execScript.getOpt());
            JGSLScript script = sp.execScript(new File(fileName));

            jgslLogger.info(script.getParseStatus());

            if (script.hasErrors()) {
                return;
            }

            String fullClassName = script.generateImplementation();
            if (line.hasOption(viewScript.getOpt())) {
// TODO: currently only swing is supported so the type is not checked here
                SwingScriptViewer ssv = new SwingScriptViewer();
                if (line.hasOption(saveToFile.getOpt())) {
                    saveToFileName = line.getOptionValue(saveToFile.getOpt());
                }
                ssv.renderScript(fullClassName, saveToFileName);
            }
            return;
        }

        if (line.hasOption(genDoc.getOpt())) {
            ScriptParser sp = new ScriptParser();
            String filenames[] = line.getOptionValues(genDoc.getOpt());
            JGSLScript script = sp.execScript(new File(filenames[0]));
            jgslLogger.info(script.getParseStatus());

            if (script.hasErrors()) {
                System.exit(1);
            }
            String docStr = script.getDocumentation();
            File docFile = new File(filenames[1]);
            try {
                FileWriter fw = new FileWriter(docFile);
                fw.write(docStr);
                fw.close();
            } catch (FileNotFoundException e) {
                jgslLogger.error(e.getMessage());
                sysLogger.error(e.getMessage(), e);
                System.exit(1);
            } catch (IOException e) {
                jgslLogger.error(e.getMessage());
                sysLogger.error(e.getMessage(), e);
                System.exit(1);
            }
            return;
        }


        if (line.hasOption(genJar.getOpt())) {
            ScriptParser sp = new ScriptParser();
            String filenames[] = line.getOptionValues(genJar.getOpt());
            JGSLScript script = sp.execScript(new File(filenames[0]));
            jgslLogger.info(script.getParseStatus());

            if (script.hasErrors()) {
                System.exit(1);
            }
            script.generateImplementation();
            try {
                JarPackager.makeJar(new File(filenames[1]), script.getClassFileName(), script.getFullClassName());
            } catch (JarPackagerException e) {
                jgslLogger.error(e.getMessage());
                sysLogger.error(e.getMessage(), e);
                System.exit(1);
            }
            return;
        }

        // if we get here then show the GUI
        JGSLSwingFrame.startJGSL(args);
    }

}
