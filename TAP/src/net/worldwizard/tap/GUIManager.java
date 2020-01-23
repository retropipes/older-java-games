/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.worldwizard.tap.resourcemanagers.GraphicsManager;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final Container guiPane;
    final JTextField commandInput;
    private final JTextArea commandOutput;
    private final CommandInputHandler ciHandler;
    private final int maxLineCount;
    private boolean cleared = true;

    // Constructors
    public GUIManager() {
        this.ciHandler = new CommandInputHandler();
        this.guiFrame = new JFrame("Text Adventure Parser (TAP)");
        this.guiPane = this.guiFrame.getContentPane();
        this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.guiPane.setLayout(new BorderLayout());
        final Image iconlogo = GraphicsManager.getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.commandInput = new JTextField(60);
        this.commandInput.addActionListener(this.ciHandler);
        this.commandOutput = new JTextArea("Welcome to TAP!", 30, 60);
        this.commandOutput.setEditable(false);
        this.commandOutput.setLineWrap(true);
        this.commandOutput.setWrapStyleWord(true);
        this.guiPane.add(this.commandOutput, BorderLayout.CENTER);
        this.guiPane.add(this.commandInput, BorderLayout.SOUTH);
        this.guiFrame.setResizable(false);
        this.guiFrame.pack();
        this.maxLineCount = this.commandOutput.getRows();
    }

    // Methods
    public JFrame getGUIFrame() {
        if (this.guiFrame.isVisible()) {
            return this.guiFrame;
        } else {
            return null;
        }
    }

    protected void showGUI() {
        final Application app = TAP.getApplication();
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().checkFlags();
    }

    public void quitHandler() {
        System.exit(0);
    }

    void processCommandInput(final String cmd) {
        if (TAP.getApplication().getAdventureManager().getLoaded()) {
            TAP.getApplication().getAdventureManager().getAdventure()
                    .parseCommand(cmd);
        } else {
            this.updateCommandOutput("No adventure opened!");
        }
    }

    public void clearCommandOutput() {
        this.commandOutput.setText("");
        this.cleared = true;
    }

    protected void updateCommandOutput(final String out) {
        if (this.cleared) {
            this.cleared = false;
            this.commandOutput.setText(out);
        } else {
            this.commandOutput.append("\n" + out);
        }
        // Check line count
        if (this.commandOutput.getLineCount() > this.maxLineCount) {
            // Clear output
            this.commandOutput.setText(out);
        }
    }

    private class CommandInputHandler implements ActionListener {
        public CommandInputHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(final ActionEvent arg0) {
            try {
                final GUIManager gum = GUIManager.this;
                // Get command
                final String cmd = gum.commandInput.getText();
                // Clear command input area
                gum.commandInput.setText("");
                // Process command
                gum.processCommandInput(cmd);
            } catch (final RuntimeException re) {
                TAP.getDebug().debug(re);
            }
        }
    }
}
