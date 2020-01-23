/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.maze.Maze;

public class MazePreferencesManager {
    // Fields
    private JFrame prefFrame;
    private Container mainPrefPane, contentPane, buttonPane;
    private JButton prefsOK, prefsCancel;
    private JComboBox<String> startLevelChoices;
    private String[] startLevelChoiceArray;
    private JTextField health;
    private JTextField mazeTitle;
    private JTextArea mazeStartMessage;
    private JTextArea mazeEndMessage;
    private EventHandler handler;

    // Constructors
    public MazePreferencesManager() {
        this.setUpGUI();
    }

    // Methods
    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        this.loadPrefs();
        MazeMode.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        MazeMode.getApplication().getEditor().enableOutput();
        MazeMode.getApplication().getMazeManager().setDirty(true);
        MazeMode.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        m.setStartLevel(this.startLevelChoices.getSelectedIndex());
        try {
            m.setMaximumHP(Integer.parseInt(this.health.getText()));
            m.healFully();
        } catch (final NumberFormatException nf) {
            // Ignore
        }
        m.setMazeTitle(this.mazeTitle.getText());
        m.setMazeStartMessage(this.mazeStartMessage.getText());
        m.setMazeEndMessage(this.mazeEndMessage.getText());
    }

    private void loadPrefs() {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        this.startLevelChoiceArray = new String[m.getLevels()];
        for (int x = 0; x < m.getLevels(); x++) {
            this.startLevelChoiceArray[x] = Integer.toString(x + 1);
        }
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                this.startLevelChoiceArray);
        this.startLevelChoices.setModel(model);
        this.startLevelChoices.setSelectedIndex(m.getStartLevel());
        this.health.setText(Integer.toString(m.getMaximumHP()));
        this.mazeTitle.setText(m.getMazeTitle());
        this.mazeStartMessage.setText(m.getMazeStartMessage());
        this.mazeEndMessage.setText(m.getMazeEndMessage());
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Maze Preferences");
        final Image iconlogo = MazeMode.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.mainPrefPane = new Container();
        this.contentPane = new Container();
        this.buttonPane = new Container();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
        this.prefsCancel = new JButton("Cancel");
        this.prefsCancel.setDefaultCapable(false);
        this.startLevelChoices = new JComboBox<>();
        this.health = new JTextField("");
        this.mazeTitle = new JTextField("");
        this.mazeStartMessage = new JTextArea("");
        this.mazeEndMessage = new JTextArea("");
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.contentPane.setLayout(new GridLayout(10, 1));
        this.contentPane.add(new JLabel("Starting Level"));
        this.contentPane.add(this.startLevelChoices);
        this.contentPane.add(new JLabel("Starting Health"));
        this.contentPane.add(this.health);
        this.contentPane.add(new JLabel("Maze Title"));
        this.contentPane.add(this.mazeTitle);
        this.contentPane.add(new JLabel("Maze Start Message"));
        this.contentPane.add(this.mazeStartMessage);
        this.contentPane.add(new JLabel("Maze End Message"));
        this.contentPane.add(this.mazeEndMessage);
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.prefsOK);
        this.buttonPane.add(this.prefsCancel);
        this.mainPrefPane.add(this.contentPane, BorderLayout.CENTER);
        this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final MazePreferencesManager mpm = MazePreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    mpm.setPrefs();
                    mpm.hidePrefs();
                } else if (cmd.equals("Cancel")) {
                    mpm.hidePrefs();
                }
            } catch (final Exception ex) {
                MazeMode.getErrorLogger().logError(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final MazePreferencesManager pm = MazePreferencesManager.this;
            pm.hidePrefs();
        }

        @Override
        public void windowClosed(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowActivated(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeactivated(final WindowEvent e) {
            // Do nothing
        }
    }
}
