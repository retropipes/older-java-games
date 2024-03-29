/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.worldwizard.worldz.resourcemanagers.GraphicsManager;

public class AboutDialog {
    // Fields
    private JFrame aboutFrame;
    private Container aboutPane, textPane, buttonPane, logoPane;
    private JButton aboutOK;
    private EventHandler handler;
    private JLabel miniLabel;

    // Constructors
    public AboutDialog(final String ver) {
        this.setUpGUI(ver);
    }

    // Methods
    public void showAboutDialog() {
        if (Worldz.inWorldz()) {
            this.aboutFrame.setVisible(true);
        }
    }

    void hideAboutDialog() {
        if (Worldz.inWorldz()) {
            this.aboutFrame.setVisible(false);
        }
    }

    private void setUpGUI(final String ver) {
        this.handler = new EventHandler();
        this.aboutFrame = new JFrame("About Worldz");
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.aboutFrame.setIconImage(iconlogo);
        this.aboutPane = new Container();
        this.textPane = new Container();
        this.buttonPane = new Container();
        this.logoPane = new Container();
        this.aboutOK = new JButton("OK");
        this.miniLabel = new JLabel("", GraphicsManager.getMiniatureLogo(),
                SwingConstants.LEFT);
        this.aboutOK.setDefaultCapable(true);
        this.aboutFrame.getRootPane().setDefaultButton(this.aboutOK);
        this.aboutFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.aboutPane.setLayout(new BorderLayout());
        this.logoPane.setLayout(new FlowLayout());
        this.logoPane.add(this.miniLabel);
        this.textPane.setLayout(new GridLayout(4, 1));
        this.textPane.add(new JLabel("Worldz Version: " + ver));
        this.textPane.add(new JLabel("Author: Eric Ahnell"));
        this.textPane
                .add(new JLabel("Web Site: http://worldz.worldwizard.net/"));
        this.textPane.add(
                new JLabel("E-mail bug reports to: worldz@worldwizard.net  "));
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.aboutOK);
        this.aboutPane.add(this.logoPane, BorderLayout.WEST);
        this.aboutPane.add(this.textPane, BorderLayout.CENTER);
        this.aboutPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.aboutFrame.setResizable(false);
        this.aboutOK.addActionListener(this.handler);
        this.aboutFrame.setContentPane(this.aboutPane);
        this.aboutFrame.pack();
    }

    public void update() {
        this.miniLabel.setIcon(GraphicsManager.getMiniatureLogo());
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final AboutDialog ad = AboutDialog.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    ad.hideAboutDialog();
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }
    }
}