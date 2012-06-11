/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.view.swing;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import jgsl.util.JGSLToImage;

/**
 * The JGSLViewer class is executed as a main class by the JGSL at runtime. It takes the suppled class name and creates
 * and instance using reflection.  This class is an subclass of BaseFrame thus represents a compiled JGSL script. It
 * assumed the the JVM and CLASSPATH are set properly by the SwingScriptViewer class.
 *
 * @author zenarchitect
 * @version $Id: JGSLViewer.java,v 1.6 2005/05/24 17:32:21 zenarchitect Exp $
 */
public class JGSLViewer {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    /**
     * Create the BaseFrame subclass and window closing actions then display the window to the user.
     *
     * @param args A single argument specifying the JGSL Java class name.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            if (args.length == 0) {
                System.exit(1);
            }

            URL props = Thread.currentThread().getContextClassLoader().getResource("jgsl/resources/jgsl_log.prop");
            if (props != null) {
                PropertyConfigurator.configure(props);
            } else {
                BasicConfigurator.configure();
            }

            String scriptClassName = args[0];

            Class scriptClass = Class.forName(scriptClassName);

            Object o = scriptClass.newInstance();

            JFrame f = (JFrame) scriptClass.cast(o);

            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent ev) {
                    System.exit(0);
                }
            });

            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);

            if (args.length == 2) {
                String fileName = args[1];
                jgslLogger.info("Create image file: " + fileName);
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

                JGSLToImage toImage = new JGSLToImage(f);

                toImage.save(fileName, fileType);
            }
        }
        catch (Exception e) {
            sysLogger.error("Exception during rendeer of JGSL script", e);
            throw e;
        }
    }

}

