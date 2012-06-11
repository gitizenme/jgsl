/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.view.swing;

import org.apache.log4j.Logger;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;


/**
 * The BaseFrame class is used as a template to generate the JGSL compiled script class. This class is loaded by
 * Javassist and renamed to the script name plus a package specification. The generated class is writting to the user's
 * home directory + ".jgsl/cache" as the full path.
 *
 * @author Joe Chavez
 * @version $Id: BaseFrame.java,v 1.8 2005/05/24 17:32:20 zenarchitect Exp $
 */
public class BaseFrame extends JFrame {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");

    public static boolean DEBUG = false;

    static
            {
                System.setProperty("com.apple.hwaccel", "false");
                System.setProperty("apple.awt.graphics.EnableQ2DX", "false");

                /**
                 * Description: Use a queue for graphics primtives
                 * (improves graphics performance of rendering simple
                 * primtives - lines, rects, arcs, ovals).
                 * Default Value: true
                 */
                System.setProperty("apple.awt.graphics.EnableLazyDrawing", "false");

                /**
                 * Description: Controls the size of a queue used by
                 * EnableLazyDrawing optimization (in entries, where 1
                 * entry = 4 bytes). One graphics primitive requires
                 * about 10 entries.
                 * Default Value: 2
                 */
                System.setProperty("apple.awt.graphics.EnableLazyDrawingQueueSize", "0");
            }

    /**
     * Constructs a new frame that is initially invisible.
     * <p/>
     * This constructor sets the component's locale property to the value returned by
     * <code>JComponent.getDefaultLocale</code>.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public BaseFrame() throws HeadlessException {
        if (DEBUG) {
            setSize(640, 480);
            setTitle("JGSL Viewer - DEBUG MODE");
        }
        //Center frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = getSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        size.height = size.height / 2;
        size.width = size.width / 2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;
        setLocation(x + 25, y + 25);

    }

    /**
     * Paints the container. This forwards the paint to any lightweight components that are children of this container.
     * If this method is reimplemented, super.paint(g) should be called so that lightweight components are properly
     * rendered. If a child component is entirely clipped by the current clipping setting in g, paint() will not be
     * forwarded to that child.
     *
     * @param g the specified Graphics window
     * @see java.awt.Component#update(java.awt.Graphics)
     */
    public void paint(java.awt.Graphics g) {
        sysLogger.info("Painting the image...");
        if (DEBUG) {
            test(g);
        }
        sysLogger.info("Painting completed.");
    }

    private void test(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        java.awt.Container canvas = this.getContentPane();

        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawLine(g2, 100, 100, 100, 100);", 25, 100, Color.WHITE);
        scriptDrawLine(g2, 100, 100, 100, 100, Color.RED);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawString(g2, \"This is a text string\", 25, 100);", 25, 125, Color.WHITE);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawLine(g2, 125, 125, 250, 175);", 25, 150, Color.WHITE);
        scriptDrawLine(g2, 125, 125, 250, 175, Color.BLUE);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawRectangle(g2, 50, 50, 100, 100);", 25, 175, Color.WHITE);
        scriptDrawRectangle(g2, 50, 50, 100, 100, Color.GREEN);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawRectangle(g2, 50, 50, 150, 200);", 25, 200, Color.WHITE);
        scriptDrawRectangle(g2, 50, 50, 150, 200, Color.YELLOW);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawCircle(g2, 320, 240, 50);", 25, 225, Color.WHITE);
        scriptDrawCircle(g2, 320, 240, 50, Color.CYAN);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptDrawElipse(g2, 320, 240, 100, 50);", 25, 250, Color.WHITE);
        scriptDrawElipse(g2, 320, 240, 100, 50, Color.ORANGE);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
        scriptDrawString(g2, "scriptArc(g2, 300, 200, 25, 50, 90, 180);", 25, 275, Color.WHITE);
        scriptDrawArc(g2, 300, 200, 25, 50, 90, 180, Color.MAGENTA);
//        scriptSleep(2);

//        scriptClearCanvas(g2);
//        scriptDrawString(g2, "scriptDrawPolygon(g2, xp, yp);", 25, 100);
//        int xp[] = {100, 125, 200, 150};
//        int yp[] = {50, 150, 125, 75};
//        scriptDrawPolygon(g2, xp, yp);
        BufferedImage gi = null;
    }

    private void genJava(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        java.awt.Container canvas = this.getContentPane();
    }

    private void scriptDrawLine(Graphics2D g2, int x1, int y1, int x2, int y2, Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawLine(x1, y1, x2, y2);
            g2.setColor(saveColor);
        } else {
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private void scriptDrawString(Graphics2D g2, String str, int x, int y, Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawString(str, x, y);
            g2.setColor(saveColor);
        } else {
            g2.drawString(str, x, y);
        }
    }

    private void scriptClearCanvas(Graphics2D g2) {
        java.awt.Container cp = this.getContentPane();

        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    private void scriptDrawRectangle(Graphics2D g2, int x1, int y1, int width, int height, Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawRect(x1, y1, width, height);
            g2.setColor(saveColor);
        } else {
            g2.drawRect(x1, y1, width, height);
        }
    }

    private void scriptDrawCircle(Graphics2D g2, int x1, int y1, double radius, Color fgColor) {
        double width = 2 * radius;
        double height = width;
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawOval(x1, y1, (int) width, (int) height);
            g2.setColor(saveColor);
        } else {
            g2.drawOval(x1, y1, (int) width, (int) height);
        }
    }

    private void scriptDrawElipse(Graphics2D g2, int x1, int y1, int width, int height, Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawOval(x1, y1, width, height);
            g2.setColor(saveColor);
        } else {
            g2.drawOval(x1, y1, width, height);
        }
        g2.drawOval(x1, y1, width, height);
    }

    private void scriptDrawArc(Graphics2D g2, int x1, int y1, int width, int height, int startAngle, int arcAngle, Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawArc(x1, y1, width, height, startAngle, arcAngle);
            g2.setColor(saveColor);
        } else {
            g2.drawArc(x1, y1, width, height, startAngle, arcAngle);
        }
        g2.drawArc(x1, y1, width, height, startAngle, arcAngle);
    }

    private void scriptDrawPolygon(Graphics2D g2, int xp[], int yp[], Color fgColor) {
        if (fgColor != null) {
            Color saveColor = g2.getColor();
            g2.setColor(fgColor);
            g2.drawPolygon(xp, yp, xp.length);
            g2.setColor(saveColor);
        } else {
            g2.drawPolygon(xp, yp, xp.length);
        }
        g2.drawPolygon(xp, yp, xp.length);
    }

    private void scriptSleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BaseFrame.DEBUG = true;
        BaseFrame f = new BaseFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setBackground(Color.BLUE);

    }
}

