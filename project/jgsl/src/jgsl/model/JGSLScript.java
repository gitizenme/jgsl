/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */

package jgsl.model;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import jgsl.io.Message;
import jgsl.io.ParseStatus;
import jgsl.io.ScriptError;
import jgsl.io.ScriptMessage;
import jgsl.io.ScriptWarning;;

/**
 * A JGSLScript contains an ordered colllection of objects that implement the statement interface.
 *
 * @author zenarchitect
 * @version $Id: JGSLScript.java,v 1.7 2005/05/21 19:21:36 zenarchitect Exp $
 */
public class JGSLScript implements Serializable, Script, ParseStatus {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    ResourceBundle res = ResourceBundle.getBundle("jgsl.view.swing.resources.BaseFrame");

    /**
     * @link aggregationByValue
     * @supplierCardinality 1..*
     * @clientCardinality 1
     * @label is composed of
     */
    /*# Statement lnkStatement; */

    private LinkedList<Statement> statements = new LinkedList<Statement>();

    private ArrayList<Message> messages = new ArrayList<Message>();
    int errorCount = 0;
    int warningCount = 0;
    int messageCount = 0;

    private String scriptName;
    private Documentation doc;

    private String className;
    private String fullClassName;
    private String classFileName;


    public JGSLScript() {
    }

    public String getClassName() {
        return className;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public String getClassFileName() {
        return classFileName;
    }

    /**
     * Get the script name
     *
     * @return String containing the script name
     */
    public String getScriptName() {
        return scriptName;
    }

    /**
     * Set the scipt name
     *
     * @param scriptName name of the script file
     */
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getJavaForInit() {
        StringBuffer strBuff = new StringBuffer(1024);

        for (Statement s : statements) {
            if (s.getType().equals(Commands.CANVAS.getName())) {
                strBuff.append(s.getJava());
            }
        }
        String title = res.getString("jgsl.title") + " - " + scriptName;
        strBuff.append("setTitle(\"" + title + "\");");

        return strBuff.toString();
    }

    /**
     * Return the Java implementation of this script
     *
     * @return the Java language implementation of this script
     */
    public String getJava() {
        StringBuffer strBuff = new StringBuffer(1024);

//        strBuff.append("super.paint(g);");
        strBuff.append("java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;");
        strBuff.append("java.awt.Container canvas = this.getContentPane();");

        for (Statement s : statements) {
            if (s.getType().equals(Commands.CANVAS.getName())) {
                continue;
            }
            strBuff.append(s.getJava());
        }

        return strBuff.toString();
    }

    public void addDocumentation(String d) {
        if (doc == null) {
            doc = new Documentation();
        }
        if (d.startsWith("\"")) {
            d = d.substring(1);
        }
        if (d.endsWith("\"")) {
            d = d.substring(0, d.length() - 1);
        }
        if (!d.endsWith("\n")) {
            d += "\n";
        }
        doc.addDoc(d);
    }

    /**
     * Returns the JGSL script documentation as specified in the DOC keyword by the script author.
     *
     * @return The script documentation
     */
    public String getDocumentation() {
        return doc.getJava();
    }

    /**
     *
     */
    public void add(Statement s) {
        statements.add(s);
    }

    /**
     * Generate the implementation class and return the name of the class
     *
     * @return returns a String containing the full name of the implementation class
     */
    public String generateImplementation() {

        className = scriptName.substring(scriptName.lastIndexOf("/") + 1, scriptName.lastIndexOf("."));
        fullClassName = "jgsl.generated." + className;
//TODO: get destination dir from command line or config file
        String dirName = System.getProperty("user.home") + File.separator + ".jgsl" + File.separator + "cache";

        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // now check for the jar files
        // if not there then write them from the resources
        File jgslJar = new File(dir, "jgsl_rt.jar");
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("lib/jgsl_rt.jar");
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(jgslJar);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte buff[] = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bis.read(buff)) != -1) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();  //TODO handle exception
        }

        File log4Jar = new File(dir, "log4j-1.2.9.jar");
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("lib/log4j-1.2.9.jar");
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(log4Jar);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte buff[] = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bis.read(buff)) != -1) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();  //TODO handle exception
        }

        File f = new File(dirName + File.separator + fullClassName);
        if (f.exists()) {
            f.delete();
        }

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(this.getClass()));
        CtClass cc = null;
        try {
            cc = pool.get("jgsl.view.swing.BaseFrame");
            cc.setName(fullClassName);

            // modify the constructor
            CtConstructor cd[] = cc.getDeclaredConstructors();

            String initStr = getJavaForInit();
            cd[0].insertBeforeBody(initStr);

            // modify the paint method
            CtMethod m = cc.getDeclaredMethod("paint");

            String paintStr = getJava();
            m.insertAfter("{" +
                    paintStr +
                    "}");

            cc.writeFile(dirName);
            cc.defrost();
            classFileName = dirName + File.separator + fullClassName.replace('.', '/');
            return fullClassName;

        } catch (NotFoundException e) {
            e.printStackTrace();  //TODO: To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //TODO: To change body of catch statement use File | Settings | File Templates.
        } catch (CannotCompileException e) {
            e.printStackTrace();  //TODO: To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public String toString() {
        return "JGSLScript{" +
                "statements=" + statements +
                ", scriptName='" + scriptName + "'" +
                "}";
    }

    /**
     * Add a ScriptError to the parse status
     *
     * @param se
     */
    public void addError(ScriptError se) {
        messages.add(se);
        errorCount++;
    }

    /**
     * Add a ScriptWarning to the parse status
     *
     * @param sw
     */
    public void addWarning(ScriptWarning sw) {
        messages.add(sw);
        warningCount++;
    }

    /**
     * Add a ScriptMessage to the parse status
     *
     * @param sm
     */
    public void addMessage(ScriptMessage sm) {
        messages.add(sm);
        messageCount++;
    }


    /**
     * Return the error state of the script
     *
     * @return true of the script contains errors or false otherwise
     */
    public boolean hasErrors() {
        return errorCount > 0;
    }

    /**
     * Return the warning state of the script
     *
     * @return true of the script contains warnings or false otherwise
     */
    public boolean hasWarnings() {
        return warningCount > 0;
    }

    /**
     * Return the message state of the script * @return true of the script contains messages or false otherwise
     */
    public boolean hasMessages() {
        return messageCount > 0;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public String getParseStatus() {
        String status = "Parse Status\n";

        for (Message m : messages) {
            status += m.getMessage();
        }

        String messageString = "messages";
        String warningString = "warnings";
        String errorString = "errors";
        if (messageCount == 1) {
            messageString = "message";
        }
        if (warningCount == 1) {
            warningString = "warning";
        }
        if (errorCount == 1) {
            errorString = "error";
        }

        status += String.format("%d %s, %d %s, %d %s encountered during script processing.\n", messageCount, messageString, warningCount, warningString, errorCount, errorString);
        if (hasErrors()) {
            status += "Please examine and correct any errors.";
        }
        return status;
    }
}
