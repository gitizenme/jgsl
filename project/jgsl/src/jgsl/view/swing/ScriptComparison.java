package jgsl.view.swing;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class ScriptComparison extends JFrame {

    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.drawLine(100, 100, 125, 125);
    }

    public static void main(String[] args) {
        ScriptComparison f = new ScriptComparison();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
