/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.URL;

import jgsl.controller.script.ScriptEngine;

/**
 * JGSL program main class. This is main entry point for executing the JGSL in both command line and GUI modes. This
 * class configures 2 loggers using the Log4J API. The Log4J properties file is bundled with the jgsl.jar
 * file and is located in the <pre>jgsl/resources/jgsl_log.prop</pre> file.
 * <p/>
 * After the loggers are configured the control flow is passed to the ScriptEngine class.
 *
 * @author zenarchitect
 * @version $Id: JGSL.java,v 1.8 2005/05/16 00:54:23 zenarchitect Exp $
 */
public class JGSL {
    static Logger jgslLogger;
    static Logger sysLogger;

    /**
     * @link aggregationByValue
     * @directed
     */
    /*# ScriptEngine lnkScriptEngine; */

            public static void main(String[] args) {
        try {
            File logFileDir = new File(System.getProperty("user.home") + File.separator + ".jgsl" + File.separator + "logs");
            logFileDir.mkdirs();
            URL props = Thread.currentThread().getContextClassLoader().getResource("jgsl/resources/jgsl_log.prop");
            if (props != null) {
                PropertyConfigurator.configure(props);
            } else {
                BasicConfigurator.configure();
            }
            jgslLogger = Logger.getLogger("jgsl_log");
            sysLogger = Logger.getLogger("jgsl_sys_log");

            jgslLogger.info("Starting JGSL");
            ScriptEngine se = new ScriptEngine();
            se.processCommandLine(args);
            jgslLogger.info("Ending JGSL");

        } catch (Throwable t) {
            jgslLogger.error("JGSL Error\n", t);
        }

    }
}