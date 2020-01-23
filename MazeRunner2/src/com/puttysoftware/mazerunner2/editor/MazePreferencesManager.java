/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor;

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

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.Maze;

public class MazePreferencesManager {
    // Fields
    private JFrame prefFrame;
    private JComboBox<String> startLevelChoices;
    private JTextField mazeTitle;
    private JTextArea mazeStartMessage;
    private JTextArea mazeEndMessage;

    // Constructors
    public MazePreferencesManager() {
        this.setUpGUI();
    }

    // Methods
    public void showPrefs() {
        this.loadPrefs();
        MazeRunnerII.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        MazeRunnerII.getApplication().getEditor().enableOutput();
        MazeRunnerII.getApplication().getMazeManager().setDirty(true);
        MazeRunnerII.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        m.setStartLevel(this.startLevelChoices.getSelectedIndex());
        m.setMazeTitle(this.mazeTitle.getText());
        m.setMazeStartMessage(this.mazeStartMessage.getText());
        m.setMazeEndMessage(this.mazeEndMessage.getText());
    }

    private void loadPrefs() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        String[] startLevelChoiceArray = new String[m.getLevels()];
        for (int x = 0; x < m.getLevels(); x++) {
            startLevelChoiceArray[x] = Integer.toString(x + 1);
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                startLevelChoiceArray);
        this.startLevelChoices.setModel(model);
        this.startLevelChoices.setSelectedIndex(m.getStartLevel());
        this.mazeTitle.setText(m.getMazeTitle());
        this.mazeStartMessage.setText(m.getMazeStartMessage());
        this.mazeEndMessage.setText(m.getMazeEndMessage());
    }

    private void setUpGUI() {
        EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame("Maze Preferences");
        final Image iconlogo = MazeRunnerII.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        Container mainPrefPane = new Container();
        Container contentPane = new Container();
        Container buttonPane = new Container();
        JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.startLevelChoices = new JComboBox<>();
        this.mazeTitle = new JTextField("");
        this.mazeStartMessage = new JTextArea("");
        this.mazeEndMessage = new JTextArea("");
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        contentPane.setLayout(new GridLayout(8, 1));
        contentPane.add(new JLabel("Starting Level"));
        contentPane.add(this.startLevelChoices);
        contentPane.add(new JLabel("Maze Title"));
        contentPane.add(this.mazeTitle);
        contentPane.add(new JLabel("Maze Start Message"));
        contentPane.add(this.mazeStartMessage);
        contentPane.add(new JLabel("Maze End Message"));
        contentPane.add(this.mazeEndMessage);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        mainPrefPane.add(contentPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                MazePreferencesManager mpm = MazePreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    mpm.setPrefs();
                    mpm.hidePrefs();
                } else if (cmd.equals("Cancel")) {
                    mpm.hidePrefs();
                }
            } catch (Exception ex) {
                MazeRunnerII.getErrorLogger().logError(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(WindowEvent e) {
            MazePreferencesManager pm = MazePreferencesManager.this;
            pm.hidePrefs();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // Do nothing
        }
    }
}
