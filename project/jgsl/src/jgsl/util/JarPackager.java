/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Create an executable JAR file for a JGSL script Java class.
 *
 * @author jchavez
 */
public class JarPackager {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    /**
     * Create a jar file for JGSL distribution. The className parameter will be used to set the main class attribute.
     * <p/>
     * Main-Class: className
     *
     * @param jarFileName   Name of JAR to create
     * @param classFileName Full path to the class file to add to the jar
     * @param className     Name of the class with full package specification. The "." will be replaced with "/".
     * @throws JarPackagerException
     */
    public static void makeJar(File jarFileName, String classFileName, String className) throws JarPackagerException {
        jgslLogger.info("Creating " + jarFileName.getAbsolutePath() + " for JGSL script " + className);
        JarOutputStream targetJar;
        FileOutputStream fos;
        Manifest manifest = new Manifest();
        Attributes attrs = manifest.getMainAttributes();

        ResourceBundle res = ResourceBundle.getBundle("jgsl.resources.JGSL");
        String version = res.getString("jgsl.app.name");

        attrs.putValue("Manifest-Version", "1.0");
        attrs.putValue("Created-By", version);
        attrs.putValue("Main-Class", className);


        // Need the following files in the JAR
        //      the JGSL class
        //      jgsl_rt.jar
        //      log4j-1.2.9.jar

        try {
            fos = new FileOutputStream(jarFileName);
            targetJar = new JarOutputStream(fos, manifest);
            mergeJar(targetJar, "jgsl_rt.jar");
            mergeJar(targetJar, "log4j-1.2.9.jar");

            addEntry(targetJar, classFileName, className);

            targetJar.flush();
            targetJar.close();
            jgslLogger.info("JAR creation completed.");
        } catch (FileNotFoundException e) {
            jgslLogger.error(e.getMessage(), e);
            throw new JarPackagerException("Unable to create JAR file: " + jarFileName.getAbsolutePath());
        } catch (IOException e) {
            jgslLogger.error(e.getMessage(), e);
            throw new JarPackagerException("Unable to create JAR file: " + jarFileName.getAbsolutePath());
        }

    }

    /**
     * Add an entry to a jar file
     *
     * @param targetJar     Output stream of the jar to add the entry to
     * @param classFileName Full path to the class file to add to the jar
     * @param className     Name of the class with full package specification. The "." will be replaced with "/".
     * @throws IOException If reading/writing encounters an error
     */
    private static void addEntry(JarOutputStream targetJar, String classFileName, String className) throws IOException {
        sysLogger.debug("BEGIN - JarPackager.addEntry");
        String jarEntryName = className.replace('.', '/') + ".class";
        JarEntry entry = new JarEntry(jarEntryName);

        targetJar.putNextEntry(entry);

        FileInputStream fis = new FileInputStream(classFileName + ".class");

        byte[] buf = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = fis.read(buf)) != -1) {
            targetJar.write(buf, 0, bytesRead);
        }
        targetJar.closeEntry();
        sysLogger.debug("END - JarPackager.addEntry");
    }

    /**
     * Merge the contents of a JAR file into another
     * <p/>
     * Looks for jarName in the JGSL cache: System.getProperty("user.home") + File.separator + ".jgsl" + File.separator
     * + "cache";
     *
     * @param jarOut  Output stream of the jar to merge into
     * @param jarName Name of the JAR file to merge will taken from the JGSL cache
     * @throws IOException Thrown if reading/writing fails.
     */
    private static void mergeJar(JarOutputStream jarOut, String jarName) throws IOException {
        sysLogger.debug("BEGIN - JarPackager.mergeJar");
        JarInputStream jarIn;
        String jgslCache = System.getProperty("user.home") + File.separator + ".jgsl" + File.separator + "cache";
        File jarFile = new File(jgslCache + File.separator + jarName);

        jarIn = new JarInputStream(new FileInputStream(jarFile));
        // Create a read buffer to be used for transferring data from the input

        byte[] buf = new byte[4096];

        // Iterate the entries

        JarEntry entry;
        while ((entry = jarIn.getNextJarEntry()) != null) {
            // Exclude the Manifest file from the old JAR

            if (entry.getName().equals("META-INF/MANIFEST.MF")) {
                continue;
            }

            // Write out the entry to the output JAR

            jarOut.putNextEntry(entry);
            int read;
            while ((read = jarIn.read(buf)) != -1) {
                jarOut.write(buf, 0, read);
            }

            jarOut.closeEntry();
        }

        // Flush and close all the streams


        jarIn.close();
        sysLogger.debug("END - JarPackager.mergeJar");
    }

}
