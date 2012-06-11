/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.io;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * ScriptParser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/23/2005</pre>
 */
public class ScriptParserTest extends TestCase {
    public ScriptParserTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseScript() throws Exception {
        ScriptParser sp;
        String fileName;
        String result;

        sp = new ScriptParser();
        fileName = "test/line.jgsl";
        result = sp.parseScript(new File(fileName));
        System.out.println(result);
        fileName = "test/simple.jgsl";
        result = sp.parseScript(new File(fileName));
        System.out.println(result);
    }

    public static Test suite() {
        return new TestSuite(ScriptParserTest.class);
    }
}
