/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.riskyrescue.Support;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.creatures.PrestigeConstants;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.creatures.party.PartyMember;

public class PrestigeViewer {
    // Fields
    static JFrame prestigeFrame;
    private static JPanel prestigePane;
    private static JPanel contentPane;
    private static JPanel buttonPane;
    private static JButton btnOK;
    private static JLabel[] prestigeValues;
    private static boolean inited = false;

    // Private Constructor
    private PrestigeViewer() {
        // Do nothing
    }

    // Methods
    public static void viewPrestige() {
        PrestigeViewer.setUpGUI();
        final PartyMember leader = PartyManager.getParty().getLeader();
        if (leader != null) {
            final long prestigeScore = leader.computePrestige();
            for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE; x++) {
                final long value = leader.getPrestigeValue(x);
                PrestigeViewer.prestigeValues[x]
                        .setForeground(Creature.getPrestigeColor(x));
                PrestigeViewer.prestigeValues[x].setText(
                        PrestigeConstants.PRESTIGE_NAMES[x] + ": " + value);
            }
            PrestigeViewer.prestigeValues[PrestigeConstants.MAX_PRESTIGE]
                    .setText("Overall Prestige: " + prestigeScore);
            PrestigeViewer.prestigeFrame.pack();
            PrestigeViewer.prestigeFrame.setVisible(true);
        }
    }

    private static void setUpGUI() {
        if (!PrestigeViewer.inited) {
            if (Support.inDebugMode()) {
                PrestigeViewer.prestigeFrame = new JFrame("Prestige (DEBUG)");
            } else {
                PrestigeViewer.prestigeFrame = new JFrame("Prestige");
            }
            PrestigeViewer.prestigePane = new JPanel();
            PrestigeViewer.prestigePane.setLayout(new BorderLayout());
            PrestigeViewer.contentPane = new JPanel();
            PrestigeViewer.contentPane.setLayout(
                    new GridLayout(PrestigeConstants.MAX_PRESTIGE + 1, 1));
            PrestigeViewer.buttonPane = new JPanel();
            PrestigeViewer.buttonPane.setLayout(new FlowLayout());
            PrestigeViewer.btnOK = new JButton("OK");
            PrestigeViewer.btnOK.addActionListener(
                    e -> PrestigeViewer.prestigeFrame.setVisible(false));
            PrestigeViewer.prestigeValues = new JLabel[PrestigeConstants.MAX_PRESTIGE
                    + 1];
            for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE + 1; x++) {
                PrestigeViewer.prestigeValues[x] = new JLabel();
                PrestigeViewer.contentPane
                        .add(PrestigeViewer.prestigeValues[x]);
            }
            PrestigeViewer.buttonPane.add(PrestigeViewer.btnOK);
            PrestigeViewer.prestigePane.add(PrestigeViewer.contentPane,
                    BorderLayout.CENTER);
            PrestigeViewer.prestigePane.add(PrestigeViewer.buttonPane,
                    BorderLayout.SOUTH);
            PrestigeViewer.prestigeFrame
                    .setContentPane(PrestigeViewer.prestigePane);
            PrestigeViewer.inited = true;
        }
    }
}
