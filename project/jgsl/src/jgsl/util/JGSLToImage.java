/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.util;


import com.sun.image.codec.jpeg.ImageFormatException;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * TODO - write java docs
 *
 * @author $Author: zenarchitect $
 * @version $Id: JGSLToImage.java,v 1.2 2005/05/24 17:32:20 zenarchitect Exp $
 */


public class JGSLToImage {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    static final boolean DEBUG = true;
    String version = "$Date: 2005/05/24 17:32:20 $ - $Revision: 1.2 $ - $Name: V1_0_0 $";

    public static final int JPEG = 99;
    public static final int GIF = 88;
    public static final int PNG = 84;
    public static final int BMP = 82;

    private JFrame comp;

    public JGSLToImage(JFrame comp) {
        this.comp = comp;
    }

    public void save(String fileName, String outType) throws IOException {
        sysLogger.info("File type is " + outType);
        if (outType.equalsIgnoreCase("bmp")) {
            save(fileName, BMP);
        } else if (outType.equalsIgnoreCase("png")) {
            save(fileName, PNG);
        } else if (outType.equalsIgnoreCase("jpg")) {
            save(fileName, JPEG);
        } else if (outType.equalsIgnoreCase("gif")) {
            save(fileName, GIF);
        }
    }

    public void save(String fileName, int outType) throws IOException {

        sysLogger.info("Saving image file...");
        BufferedImage   image = new BufferedImage(comp.getWidth(),
                comp.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g2 = image.createGraphics();
        g2.setClip(0, 0, comp.getWidth(), comp.getHeight());
        comp.paint(g2);
        sysLogger.info("Image size is " + image.getData().getDataBuffer().getSize());
        if (DEBUG) {
            sysLogger.debug("BufferedImage width = " + image.getWidth());
            sysLogger.debug("BufferedImage height = " + image.getHeight());
        }
        FileOutputStream out = new FileOutputStream(fileName);

        if (outType == BMP) {
            ImageIO.write(image, "bmp", out);
        } else if (outType == PNG) {
            ImageIO.write(image, "png", out);
        } else if (outType == JPEG) {
            try {
                //ImageIO.write(image, "jpg", out);
                Iterator writers = ImageIO.getImageWritersByFormatName("jpg");
                ImageWriter writer = (ImageWriter) writers.next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(out);
                writer.setOutput(ios);
                //ImageWriteParam param= writer.getDefaltWriteParam();
                //param.setCompressionQuality(1.0F);
                writer.write(image);


                //JPEGImageEncoder jpeg= JPEGCodec.createJPEGEncoder(out);
                //jpeg.encode(image);
            } catch (ImageFormatException e) {
                throw e;
            }
        } else if (outType == GIF) {
            GifEncoder encoder = new GifEncoder(image, out);
            encoder.encode();
        }
        out.flush();
        out.close();
        sysLogger.info("Save completed.");
    }
}
