/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.view.swing;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import jgsl.view.ScriptViewer;


/**
 * The SwingScriptViewer class creates an JVM that executes the JGSLViewer class with an argument of the compiled JGSL
 * script Java class.
 *
 * @author zenarchitect
 * @version $Id: SwingScriptViewer.java,v 1.7 2005/05/24 17:32:21 zenarchitect Exp $
 */

public class SwingScriptViewer implements ScriptViewer {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");
    static final boolean DEBUG = false;

    /**
     * Reder the script by creating a Process object with the properly JGSL runtime class path. The runtime classpath
     * includes the compile JGSL script in the form of a Java class. Also required on the classpath are the jgsl_rt.jar
     * and log4j-1.2.9.jar files.
     *
     * @param fullClassName
     */
    public void renderScript(String fullClassName, String saveToFileName) {
        sysLogger.debug("BEGIN - renderScript");
        // The script is rendered in a swing gui by creating a new class from the script
        // that is a subclass of a base JFrame that overrides the paint method
        try {
            sysLogger.debug("fullClassName = " + fullClassName);
            ProcessBuilder pb;
            if (saveToFileName != null) {
                pb = new ProcessBuilder(System.getProperty("java.home") +
                        File.separator + "bin" +
                        File.separator + "java", "" +
                        "jgsl.view.swing.JGSLViewer",
                        fullClassName,
                        saveToFileName);
            } else {
                pb = new ProcessBuilder(System.getProperty("java.home") +
                        File.separator + "bin" +
                        File.separator + "java", "" +
                        "jgsl.view.swing.JGSLViewer",
                        fullClassName);
            }
            if (DEBUG) {
                pb.redirectErrorStream(true);
            }
            String jgslCache = System.getProperty("user.home") + File.separator + ".jgsl" + File.separator + "cache";
            String classPath = System.getProperty("java.class.path");
            classPath += File.pathSeparator + jgslCache;
            classPath += File.pathSeparator + jgslCache + File.separator + "jgsl_rt.jar";
            classPath += File.pathSeparator + jgslCache + File.separator + "log4j-1.2.9.jar";
            sysLogger.debug("Class path = " + classPath);
            Map<String, String> env = pb.environment();
            env.put("CLASSPATH", classPath);

            Process p = pb.start();

            if (DEBUG) {
                InputStream is = p.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            sysLogger.debug("END - renderScript");

        } catch (IOException e) {
            e.printStackTrace();  // TODO: Handle exception
            sysLogger.debug(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // TODO: Handle exception
            sysLogger.debug(e.getMessage());
        }
    }

    /**
     * @directed
     */
    /*# JGSLViewer lnkJGSLViewer; */
}
