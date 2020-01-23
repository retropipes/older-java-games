/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor;

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

import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.world.World;

public class WorldPreferencesManager {
    // Fields
    private JFrame prefFrame;
    private Container mainPrefPane, contentPane, buttonPane;
    private JButton prefsOK, prefsCancel;
    private JComboBox<String> startLevelChoices;
    private String[] startLevelChoiceArray;
    private JTextField worldTitle;
    private JTextArea worldStartMessage;
    private JTextArea worldEndMessage;
    private EventHandler handler;

    // Constructors
    public WorldPreferencesManager() {
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
        Worldz.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        Worldz.getApplication().getEditor().enableOutput();
        Worldz.getApplication().getWorldManager().setDirty(true);
        Worldz.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        m.setStartLevel(this.startLevelChoices.getSelectedIndex());
        m.setWorldTitle(this.worldTitle.getText());
        m.setWorldStartMessage(this.worldStartMessage.getText());
        m.setWorldEndMessage(this.worldEndMessage.getText());
    }

    private void loadPrefs() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        this.startLevelChoiceArray = new String[m.getLevels()];
        for (int x = 0; x < m.getLevels(); x++) {
            this.startLevelChoiceArray[x] = Integer.toString(x + 1);
        }
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                this.startLevelChoiceArray);
        this.startLevelChoices.setModel(model);
        this.startLevelChoices.setSelectedIndex(m.getStartLevel());
        this.worldTitle.setText(m.getWorldTitle());
        this.worldStartMessage.setText(m.getWorldStartMessage());
        this.worldEndMessage.setText(m.getWorldEndMessage());
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("World Preferences");
        final Image iconlogo = Worldz.getApplication().getIconLogo();
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
        this.worldTitle = new JTextField("");
        this.worldStartMessage = new JTextArea("");
        this.worldEndMessage = new JTextArea("");
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.contentPane.setLayout(new GridLayout(8, 1));
        this.contentPane.add(new JLabel("Starting Level"));
        this.contentPane.add(this.startLevelChoices);
        this.contentPane.add(new JLabel("World Title"));
        this.contentPane.add(this.worldTitle);
        this.contentPane.add(new JLabel("World Start Message"));
        this.contentPane.add(this.worldStartMessage);
        this.contentPane.add(new JLabel("World End Message"));
        this.contentPane.add(this.worldEndMessage);
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
                final WorldPreferencesManager mpm = WorldPreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    mpm.setPrefs();
                    mpm.hidePrefs();
                } else if (cmd.equals("Cancel")) {
                    mpm.hidePrefs();
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final WorldPreferencesManager pm = WorldPreferencesManager.this;
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
