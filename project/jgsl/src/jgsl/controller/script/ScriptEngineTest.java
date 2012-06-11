/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.controller.script;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

/**
 * ScriptParser JUnit tester.
 *
 * @author zenarchitect
 * @version $Id: ScriptEngineTest.java,v 1.5 2005/05/21 01:42:06 zenarchitect Exp $
 */
public class ScriptEngineTest extends TestCase {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    public ScriptEngineTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        URL props = this.getClass().getResource("/jgsl/resources/jgsl_log.prop");
        if (props != null) {
            PropertyConfigurator.configure(props);
        } else {
            BasicConfigurator.configure();
        }
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testScriptEngine() throws Exception, ScriptEngineException {
        sysLogger.debug("BEGIN: testScriptEngine()");
        ScriptEngine se;
        String fileName;

        se = new ScriptEngine();
        fileName = "test/script_engine.jgsl";
        String args[] = {"-e", fileName};
        se.processCommandLine(args);
        args[1] = "test/all_errors.jgsl";
        se.processCommandLine(args);
        sysLogger.debug("END: testScriptEngine()");
    }

    public void testScriptViewer() throws Exception, ScriptEngineException {
        ScriptEngine se;
        String fileName;
        String result;

        se = new ScriptEngine();
        fileName = "test/all_commands.jgsl";
        String args[] = {"-e", fileName, "-v", "swing"};
        se.processCommandLine(args);
    }

    public void testScriptSaveToFile() throws Exception, ScriptEngineException {
        ScriptEngine se;
        String fileName;
        String result;

        fileName = "test/all_commands_mac_PNG.jgsl";
        String argsPNG[] = {"-e", fileName, "-v", "swing", "-f", fileName + ".png"};
        se = new ScriptEngine();
        se.processCommandLine(argsPNG);

        fileName = "test/all_commands_mac_GIF.jgsl";
        String argsGIF[] = {"-e", fileName, "-v", "swing", "-f", fileName + ".gif"};
        se = new ScriptEngine();
        se.processCommandLine(argsGIF);

        fileName = "test/all_commands_mac_BMP.jgsl";
        String argsBMP[] = {"-e", fileName, "-v", "swing", "-f", fileName + ".bmp"};
        se = new ScriptEngine();
        se.processCommandLine(argsBMP);

        fileName = "test/all_commands_mac_JPG.jgsl";
        String argsJPG[] = {"-e", fileName, "-v", "swing", "-f", fileName + ".jpg"};
        se = new ScriptEngine();
        se.processCommandLine(argsJPG);
    }

    public void testGenDocs() throws Exception, ScriptEngineException {
        ScriptEngine se;
        String fileName;
        String result;

        se = new ScriptEngine();
        fileName = "test/all_commands.jgsl";
        String args[] = {"-d", fileName, "test/all_commands.txt"};
        se.processCommandLine(args);
    }

    public void testGenJar() throws Exception, ScriptEngineException {
        ScriptEngine se;
        String fileName;
        String result;

        se = new ScriptEngine();
        fileName = "test/all_commands.jgsl";
        String args[] = {"-j", fileName, "test/all_commands.jar"};
        se.processCommandLine(args);
    }


    public static Test suite() {
        return new TestSuite(ScriptEngineTest.class);
    }
}
