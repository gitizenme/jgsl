/*
 * Copyright (c) 2005 Perception Software. All Rights Reserved.
 */
package jgsl.view.swing;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import jgsl.controller.script.ScriptEngine;
import jgsl.controller.script.ScriptEngineException;
import jgsl.io.ImageFileFilter;
import jgsl.io.JARFileFilter;
import jgsl.io.JGSLFileFilter;
import jgsl.io.ScriptParserException;
import jgsl.model.JGSLScript;

/**
 * The JGSLSwingFrame class is the main class for the interactive GUI.
 *
 * @author zenarchitect
 * @version $Id: JGSLSwingFrame.java,v 1.6 2005/05/24 17:32:21 zenarchitect Exp $
 */
public class JGSLSwingFrame implements ActionListener, ItemListener, ListSelectionListener {
    static Logger jgslLogger = Logger.getLogger("jgsl_log");
    static Logger sysLogger = Logger.getLogger("jgsl_sys_log");


    ResourceBundle res = ResourceBundle.getBundle("jgsl.view.swing.resources.JGSLSwingFrame", new Locale("en", "US"), Thread.currentThread().getContextClassLoader());

    private File currentFileName = new File("newfile.jgsl");
    private File newFileName;
    private boolean isNew = false;

    private JFrame frame;

    private JPanel mainPanel;
    private JTextArea scriptTextArea;
    private JPanel scriptPanel;
    private JButton viewButton;
    private JButton preferencesButton;
    private JButton quitButton;
    private JPanel actionPanel;
    private JPanel quitPanel;


    private JPanel statusPanel;
    private JList statusList;
    private JScrollPane statusScrollPane;

    JMenuBar menuBar;

    JMenu fileMenu;
    JMenuItem newMenuItem;
    JMenuItem openMenuItem;
    JMenuItem saveMenuItem;
    JMenuItem saveAsMenuItem;
    JMenuItem exitMenuItem;

    JMenu viewMenu;

    JMenu helpMenu;
    JMenuItem helpMenuItem;
    JMenuItem tutorialMenuItem;
    JMenuItem aboutMenuItem;

    DefaultListModel statusListModel = new DefaultListModel();
    private int MAX_STATUS_LIST_SIZE = 250;
    private JScrollPane scriptScrollPane;
    private String frameTitle;
    private JButton jarButton;
    private JTextField scriptOutputFileName;
    private JButton selectScriptOutputButton;
    private JCheckBox saveSciptOutputCheckBox;
    private boolean saveScriptOuput;

    String basicScript = "/*\n" +
            "JGSL Script\n" +
            "*/\n" +
            "\n" +
            "begin\n" +
            "\n" +
            "// Initialize the canvas\n" +
            "canvas (640, 480, BLACK, WHITE, \"jgsl script\");\n" +
            "\n" +
            "end\n";

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
    public JGSLSwingFrame(JFrame frame) throws HeadlessException {
        sysLogger.debug("BEGIN: JGSLSwingFrame");

        this.frame = frame;

        //Center frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        size.height = size.height / 2;
        size.width = size.width / 2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;
        frame.setLocation(x, y);
        sysLogger.debug("JGSLSwingFrame: frame setup completed...");

        if (viewButton == null) {
            sysLogger.debug("JGSLSwingFrame: goButton == null");
        }

        viewButton.addActionListener(this);

        jarButton.addActionListener(this);
        selectScriptOutputButton.addActionListener(this);
//        quitButton.addActionListener(this);

        saveSciptOutputCheckBox.addItemListener(this);

        statusList.addListSelectionListener(this);
        statusList.setModel(statusListModel);
        sysLogger.debug("JGSLSwingFrame: status list setup completed...");


        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(this);
        openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(this);
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this);
        saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(this);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);

        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);


        viewMenu = new JMenu();


        helpMenu = new JMenu("Help");
        helpMenuItem = new JMenuItem("Help Topics");
        helpMenuItem.addActionListener(this);
        tutorialMenuItem = new JMenuItem("JGSL Tutorial");
        tutorialMenuItem.addActionListener(this);
        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(this);

        helpMenu.add(helpMenuItem);
        helpMenu.add(tutorialMenuItem);
        helpMenu.addSeparator();;
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
//        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        sysLogger.debug("JGSLSwingFrame: menu bar setup completed...");


        frame.setJMenuBar(menuBar);
        sysLogger.debug("JGSLSwingFrame: menu bar added to main window...");


        frameTitle = res.getString("jgsl.i-gui.title");
        if (frameTitle != null) {
            frame.setTitle(frameTitle);
        } else {
            frame.setTitle("JGSL 1.0 - Java Web Start");
        }
        sysLogger.debug("JGSLSwingFrame: window title set...");

        addStatusItem("JGSL 1.0 ready...");

        sysLogger.debug("END: JGSLSwingFrame");

    }

    /**
     * Return reference to the main panel.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Start the JGSL. This method is needed to work around the strange startup requirements by the IntelliJ IDEA GUI
     * builder.
     */
    public static void startJGSL(String[] args) {
        sysLogger.debug("BEGIN: startJGSL");
        JFrame f = new JFrame();

        f.setSize(640, 480);

        JGSLSwingFrame mainFrame = new JGSLSwingFrame(f);

        f.setContentPane(mainFrame.getMainPanel());

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        f.pack();
        f.setVisible(true);
        sysLogger.debug("END: startJGSL");

    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        startJGSL(args);
    }

    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == quitButton || actionEvent.getSource() == exitMenuItem) {
            frame.setVisible(false);
            System.exit(0);
        } else if (actionEvent.getSource() == newMenuItem) {
            newScript();
        } else if (actionEvent.getSource() == helpMenuItem) {
            showHelpScript();
        } else if (actionEvent.getSource() == tutorialMenuItem) {
            showTutorialScript();
        } else if (actionEvent.getSource() == aboutMenuItem) {
            showAboutDialog();
        } else if (actionEvent.getSource() == openMenuItem) {
            openScript();
        } else if (actionEvent.getSource() == saveMenuItem) {
            storeScript();
        } else if (actionEvent.getSource() == viewButton) {
            viewScript();
        } else if (actionEvent.getSource() == jarButton) {
            jarScript();
        } else if (actionEvent.getSource() == selectScriptOutputButton) {
            selectScriptOutputFile();
        } else if (actionEvent.getSource() == saveAsMenuItem) {
            saveAsScript();
        }
    }

    private void showTutorialScript() {
        storeScript();
        try {
            InputStreamReader isr = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("jgsl/resources/jgsl_tutorial.jgsl"));
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            StringBuffer sb = new StringBuffer(1024);
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            isr.close();
            scriptTextArea.setText(sb.toString());
            addStatusItem("JGSL Tutorial script loaded.");
            frame.setTitle(frameTitle + " - JGSL Tutorial");
        } catch (Exception e1) {
            e1.printStackTrace();
            addStatusItem("Unable to read file tutorial file, creating basic script.");
//            scriptTextArea.setText(basicScript);
            return;
        }
        isNew = true;
    }

    private void showAboutDialog() {
        AboutDialog dialog = new AboutDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showHelpScript() {
        storeScript();
        try {
            InputStreamReader isr = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("jgsl/resources/online_help.jgsl"));
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            StringBuffer sb = new StringBuffer(1024);
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            isr.close();
            scriptTextArea.setText(sb.toString());
            addStatusItem("Online help script loaded.");
            frame.setTitle(frameTitle + " - Online Help");
        } catch (Exception e1) {
            e1.printStackTrace();
            addStatusItem("Unable to read file online help file, creating basic script.");
//            scriptTextArea.setText(basicScript);
            return;
        }
        isNew = true;
    }

    private void selectScriptOutputFile() {
        JFileChooser chooser = new JFileChooser();
        ImageFileFilter filter = new ImageFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(getMainPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File outputFileName = chooser.getSelectedFile();
            this.scriptOutputFileName.setText(outputFileName.getAbsolutePath());
        }
    }

    private void jarScript() {
        JFileChooser chooser = new JFileChooser();
        JARFileFilter filter = new JARFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(getMainPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File jarFileName = chooser.getSelectedFile();
            if (!jarFileName.getName().endsWith(".jar")) {
                jarFileName = new File(jarFileName.getAbsolutePath() + ".jar");
            }
            // parse and validate
            // generate implementation class
            // execute viewer
            ScriptEngine se = new ScriptEngine();
            try {
                JGSLScript script = se.jarInteractive(currentFileName, jarFileName);
                if (script.hasErrors()) {
// TODO - Get the errors as an object and add to status list
// TODO - Make status list context aware and select error to show user in script
                    addStatusItem(script.getParseStatus());
                }
            } catch (ScriptEngineException e) {
                e.printStackTrace();
                addStatusItem("An error was encontered creating a JAR for your script, details are provided below");
                addStatusItem(e.getMessage());
            } catch (ScriptParserException e) {
                e.printStackTrace();
                addStatusItem("An error was encontered parsing your script, details are provided below");
                addStatusItem(e.getMessage());
            }
        }
    }

    private void storeScript() {
        if (isNew) {
            saveAsScript();
        } else {
            newFileName = currentFileName;
            saveScript();
        }

    }

    private void viewScript() {
        // save the script
        storeScript();

        // parse and validate
        // generate implementation class
        // execute viewer
        ScriptEngine se = new ScriptEngine();
        try {
            String scriptOutputFileName = null;
            if (saveScriptOuput) {
                scriptOutputFileName = this.scriptOutputFileName.getText();
            }
            JGSLScript script = se.viewInteractive(currentFileName, scriptOutputFileName); // TODO add file name to GUI
            if (script.hasErrors()) {
// TODO - Make status list context aware and select error to show user in script
                addStatusItem(script.getParseStatus());
            }
        } catch (ScriptParserException e) {
            e.printStackTrace();
            addStatusItem("An error was encontered parsing your script, details are provided below");
            addStatusItem(e.getMessage());
        }

    }

    public void itemStateChanged(ItemEvent itemEvent) {

        if (itemEvent.getSource() == saveSciptOutputCheckBox) {
            saveScriptOuput = !saveScriptOuput;
        }
    }

    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        //TODO: implement method
    }

    private void newScript() {
        try {
            InputStreamReader isr = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("jgsl/resources/template.jgsl"));
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            StringBuffer sb = new StringBuffer(1024);
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            isr.close();
            scriptTextArea.setText(sb.toString());
            addStatusItem("Template script loaded, use the script editor write your script.");
            frame.setTitle(frameTitle + " - (new)");
        } catch (Exception e1) {
            e1.printStackTrace();
            addStatusItem("Unable to read file template file, creating basic script.");
            scriptTextArea.setText(basicScript);
        }
        isNew = true;
    }

    private void saveScript() {
        addStatusItem("Saving " + newFileName);
        try {
            FileWriter fw = new FileWriter(newFileName);
            fw.write(scriptTextArea.getText(), 0, scriptTextArea.getText().length());
            fw.close();
            currentFileName = newFileName;
            addStatusItem("Saved file " + newFileName);
            isNew = false;
            frame.setTitle(frameTitle + " - " + currentFileName);
        } catch (IOException e1) {
            e1.printStackTrace();
            addStatusItem("Unable to write file: " + newFileName);
        }
    }

    private void saveAsScript() {
        JFileChooser chooser = new JFileChooser();
        JGSLFileFilter filter = new JGSLFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(getMainPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFileName = chooser.getSelectedFile();
            if (!newFileName.getName().endsWith(".jgsl")) {
                newFileName = new File(newFileName.getAbsolutePath() + ".jgsl");
            }
            saveScript();
        }
    }

    private void openScript() {
        JFileChooser chooser = new JFileChooser();
        JGSLFileFilter filter = new JGSLFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(getMainPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File newFileName = chooser.getSelectedFile();
            addStatusItem("Opening " + newFileName);
            try {
                FileReader fr = new FileReader(newFileName);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                StringBuffer sb = new StringBuffer(1024);
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                fr.close();
                scriptTextArea.setText(sb.toString());
                scriptTextArea.setCaretPosition(0);
                currentFileName = newFileName;
                addStatusItem("Opened " + newFileName);
                isNew = false;
                frame.setTitle(frameTitle + " - " + currentFileName);
            } catch (IOException e1) {
                e1.printStackTrace();
                addStatusItem("Unable to read file: " + newFileName);
            }
        }
    }

    private void addStatusItem(String item) {
        if (statusListModel.getSize() > MAX_STATUS_LIST_SIZE * 1.25) {
            statusListModel.removeRange(MAX_STATUS_LIST_SIZE, (int) ((MAX_STATUS_LIST_SIZE * 1.25) - 1));
        }
        statusListModel.addElement(item);
        statusList.ensureIndexIsVisible(statusListModel.size() - 1);

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your
     * code!
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        scriptPanel = new JPanel();
        scriptPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(scriptPanel, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(320, 200), new Dimension(640, 480), null));
        scriptPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Script Editor"));
        scriptScrollPane = new JScrollPane();
        scriptPanel.add(scriptScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null));
        scriptTextArea = new JTextArea();
        scriptScrollPane.setViewportView(scriptTextArea);
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(actionPanel, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        actionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Scipt Actions"));
        viewButton = new JButton();
        viewButton.setText("View");
        viewButton.setToolTipText("View your JGLS script.");
        actionPanel.add(viewButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        jarButton = new JButton();
        jarButton.setText("Create JAR");
        jarButton.setToolTipText("Create an executable JAR file from your JGSL script.");
        actionPanel.add(jarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        final JPanel panel0 = new JPanel();
        panel0.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel0, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
        selectScriptOutputButton = new JButton();
        selectScriptOutputButton.setText("Select");
        selectScriptOutputButton.setToolTipText("Press this button to enter a file name.");
        panel0.add(selectScriptOutputButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        scriptOutputFileName = new JTextField();
        scriptOutputFileName.setEditable(false);
        scriptOutputFileName.setInheritsPopupMenu(false);
        scriptOutputFileName.setText("jgsl_image.jpg");
        scriptOutputFileName.setToolTipText("File name to which your JGSL script image will be saved to.");
        panel0.add(scriptOutputFileName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null));
        saveSciptOutputCheckBox = new JCheckBox();
        saveSciptOutputCheckBox.setText("Save script output");
        saveSciptOutputCheckBox.setToolTipText("Check this to generate an image from your JGSL script.");
        panel0.add(saveSciptOutputCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(statusPanel, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Status"));
        statusScrollPane = new JScrollPane();
        statusPanel.add(statusScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null));
        statusList = new JList();
        statusList.setToolTipText("Status of JGSL actions.");
        statusScrollPane.setViewportView(statusList);
    }
}
