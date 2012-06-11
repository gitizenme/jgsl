/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.model;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Enum of all possible commands and their corresponding command template and java code representation.
 *
 * @author zenarchitect
 * @version $Id: Commands.java,v 1.4 2005/05/24 17:32:18 zenarchitect Exp $
 */

public enum Commands {

    DRAW("draw"),
    WAIT("wait"),
    CLEAR("clear"),
    LINE("line"),
    SQUARE("square"),
    RECTANGLE("rectangle"),
    CIRCLE("circle"),
    ELIPSE("elipse"),
    ARC("arc"),
    POLYGON("polygon"),
    CANVAS("canvas"),
    TEXT("text"),
    LOG("log"),
    DEBUG("debug"),
    ERROR("error"),
    WARNING("warning");

    private String name;

    private Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCommandTemplate() {
        switch (this) {
            case CLEAR:
                return "scriptClearCanvas(g2);\n";
            case WAIT:
                return "scriptSleep(%s);\n";
            case DRAW:
            case LINE:
                return "%s\n" +
                        "scriptDrawLine(g2, %s, %s, %s, %s, %s);\n";
            case SQUARE:
            case RECTANGLE:
                return "%s\n" +
                        "scriptDrawRectangle(g2, %s, %s, %s, %s, %s);\n";
            case CIRCLE:
                return "%s\n" +
                        "scriptDrawCircle(g2, %s, %s, %s, %s);\n";
            case ELIPSE:
                return "%s\n" +
                        "scriptDrawElipse(g2, %s, %s, %s, %s, %s);\n";
            case ARC:
                return "%s\n" +
                        "scriptDrawArc(g2, %s, %s, %s, %s, %s, %s, %s);\n";
            case POLYGON:
                return "%s\n" +
                        "int xp[] = {%s};\n" +
                        "int xp[] = {%s};\n" +
                        "scriptDrawPolygon(g2, xp, yp, %s);\n";
            case CANVAS:
                return  "%s\n" +
                        "%s\n" +
                        "this.setSize(%s, %s);\n" +
                        "this.setBackground(%s);\n" +
                        "this.setForeground(%s);\n" +
                        "this.setTitle(%s);\n";
            case TEXT:
                return "%s\n" +
                        "scriptDrawString(g2, %s, %s, %s, %s);\n";
            case LOG:
                return "jgslLogger.info(%s);\n";
            case WARNING:
                return "jgslLogger.warn(%s);\n";
            case ERROR:
                return "jgslLogger.error(%s);\n";
            case DEBUG:
                return "jgslLogger.debug(%s);\n";
            default:
                return "jgslLogger.debug(\"Unknown command\")\n";
        }
    }

    public String getFormattedCommand(ArrayList<Argument> attributes, ArrayList<Argument> parameters) {
        String cmd = this.getCommandTemplate();
        String colorVarName = "null";
        String colorDecl = "";
        switch (this) {
            case CLEAR:
                return cmd;
            case WAIT:
                JGSLInteger duration = (JGSLInteger) parameters.get(0);
                return String.format(cmd, duration.getJavaValue());
            case DRAW:
            case LINE:
                JGSLInteger x1 = (JGSLInteger) parameters.get(0);
                JGSLInteger y1 = (JGSLInteger) parameters.get(1);
                JGSLInteger x2 = (JGSLInteger) parameters.get(2);
                JGSLInteger y2 = (JGSLInteger) parameters.get(3);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, x1.getJavaValue(), y1.getJavaValue(), x2.getJavaValue(), y2.getJavaValue(), colorVarName);
            case SQUARE:
                JGSLInteger xs = (JGSLInteger) parameters.get(0);
                JGSLInteger ys = (JGSLInteger) parameters.get(1);
                JGSLInteger ws = (JGSLInteger) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xs.getJavaValue(), ys.getJavaValue(), ws.getJavaValue(), ws.getJavaValue(), colorVarName);
            case RECTANGLE:
                JGSLInteger xr = (JGSLInteger) parameters.get(0);
                JGSLInteger yr = (JGSLInteger) parameters.get(1);
                JGSLInteger wr = (JGSLInteger) parameters.get(2);
                JGSLInteger hr = (JGSLInteger) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xr.getJavaValue(), yr.getJavaValue(), wr.getJavaValue(), hr.getJavaValue(), colorVarName);
            case CIRCLE:
                JGSLInteger xc = (JGSLInteger) parameters.get(0);
                JGSLInteger yc = (JGSLInteger) parameters.get(1);
                JGSLDouble rc = (JGSLDouble) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xc.getJavaValue(), yc.getJavaValue(), rc.getJavaValue(), colorVarName);
            case ELIPSE:
                JGSLInteger xe = (JGSLInteger) parameters.get(0);
                JGSLInteger ye = (JGSLInteger) parameters.get(1);
                JGSLInteger we = (JGSLInteger) parameters.get(2);
                JGSLInteger he = (JGSLInteger) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xe.getJavaValue(), ye.getJavaValue(), we.getJavaValue(), he.getJavaValue(), colorVarName);
            case ARC:
                JGSLInteger xa = (JGSLInteger) parameters.get(0);
                JGSLInteger ya = (JGSLInteger) parameters.get(1);
                JGSLInteger wa = (JGSLInteger) parameters.get(2);
                JGSLInteger ha = (JGSLInteger) parameters.get(2);
                JGSLInteger sa = (JGSLInteger) parameters.get(2);
                JGSLInteger aa = (JGSLInteger) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xa.getJavaValue(), ya.getJavaValue(), wa.getJavaValue(), ha.getJavaValue(),
                        sa.getJavaValue(), aa.getJavaValue(), colorVarName);
            case POLYGON:
                String xp = "";
                String yp = "";
                for (int i = 0; i < parameters.size(); i += 2) {
                    xp += ((JGSLInteger) parameters.get(i)).getJavaValue();
                    yp += ((JGSLInteger) parameters.get(i + 1)).getJavaValue();
                    if (i < parameters.size() - 2) {
                        xp += ",";
                        yp += ",";
                    }
                }
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, xp, yp, colorVarName);
            case TEXT:
                JGSLInteger x = (JGSLInteger) parameters.get(0);
                JGSLInteger y = (JGSLInteger) parameters.get(1);
                JGSLString text = (JGSLString) parameters.get(2);
                if (attributes.size() == 1) {
                    JGSLColor color = (JGSLColor) attributes.get(0);
                    colorVarName = color.getName();
                    colorDecl = color.getJavaValue();
                }
                return String.format(cmd, colorDecl, text.getJavaValue(), x.getJavaValue(), y.getJavaValue(), colorVarName);
            case CANVAS:
                JGSLColor bg = (JGSLColor) parameters.get(0);
                JGSLColor fg = (JGSLColor) parameters.get(1);
                JGSLInteger w = (JGSLInteger) parameters.get(2);
                JGSLInteger h = (JGSLInteger) parameters.get(3);
                String title = "\"" + ResourceBundle.getBundle("jgsl.resources.JGSL").getString("app.viewer.default-name") + "\"";
                if (parameters.size() == 5) {
                    title = ((JGSLString) parameters.get(4)).getJavaValue();
                }
                return String.format(cmd, bg.getJavaValue(), fg.getJavaValue(), w.getJavaValue(), h.getJavaValue(), bg.getName(), fg.getName(), title);
            case LOG:
                JGSLString logMsg = (JGSLString) parameters.get(0);
                return String.format(cmd, logMsg.getJavaValue());
            case WARNING:
                JGSLString warningMsg = (JGSLString) parameters.get(0);
                return String.format(cmd, warningMsg.getJavaValue());
            case ERROR:
                JGSLString errorMsg = (JGSLString) parameters.get(0);
                return String.format(cmd, errorMsg.getJavaValue());
            case DEBUG:
                JGSLString debugMsg = (JGSLString) parameters.get(0);
                return String.format(cmd, debugMsg.getJavaValue());
            default:
                return "System.out.println(\"Unknown command\")";
        }
    }
}
