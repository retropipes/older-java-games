/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.desktop.AboutEvent;
import java.awt.desktop.AboutHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.puttysoftware.mazer5d.assetmanagers.LogoManager;

public class AboutDialog implements AboutHandler {
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
        this.aboutFrame.setVisible(true);
    }

    void hideAboutDialog() {
        this.aboutFrame.setVisible(false);
    }

    private void setUpGUI(final String ver) {
        this.handler = new EventHandler();
        this.aboutFrame = new JFrame("About Mazer5D");
        final Image iconlogo = LogoManager.getLogo();
        this.aboutFrame.setIconImage(iconlogo);
        this.aboutPane = new Container();
        this.textPane = new Container();
        this.buttonPane = new Container();
        this.logoPane = new Container();
        this.aboutOK = new JButton("OK");
        this.miniLabel = new JLabel("", LogoManager.getMiniatureLogo(),
                SwingConstants.LEFT);
        this.aboutOK.setDefaultCapable(true);
        this.aboutFrame.getRootPane().setDefaultButton(this.aboutOK);
        this.aboutFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.aboutPane.setLayout(new BorderLayout());
        this.logoPane.setLayout(new FlowLayout());
        this.logoPane.add(this.miniLabel);
        this.textPane.setLayout(new GridLayout(4, 1));
        this.textPane.add(new JLabel("Mazer5D Version: " + ver));
        this.textPane.add(new JLabel("Author: Eric Ahnell"));
        this.textPane.add(
                new JLabel("Web Site: http://www.puttysoftware.com/mazer5d/"));
        this.textPane.add(new JLabel(
                "E-mail bug reports to: products@puttysoftware.com  "));
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

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            final AboutDialog ad = AboutDialog.this;
            final String cmd = e.getActionCommand();
            if (cmd.equals("OK")) {
                ad.hideAboutDialog();
            }
        }
    }

    @Override
    public void handleAbout(AboutEvent inE) {
        this.showAboutDialog();
    }
}