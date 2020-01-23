/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.creatures.PrestigeConstants;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;

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
        setUpGUI();
        PartyMember leader = PartyManager.getParty().pickOnePartyMember();
        if (leader != null) {
            long prestigeScore = leader.computePrestige();
            for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE; x++) {
                long value = leader.getPrestigeValue(x);
                prestigeValues[x].setForeground(AbstractCreature
                        .getPrestigeColor(x));
                prestigeValues[x].setText(PrestigeConstants.PRESTIGE_NAMES[x]
                        + ": " + value);
            }
            prestigeValues[PrestigeConstants.MAX_PRESTIGE]
                    .setText("Overall Prestige: " + prestigeScore);
            prestigeFrame.pack();
            prestigeFrame.setVisible(true);
        }
    }

    private static void setUpGUI() {
        if (!inited) {
            if (DungeonDiver4.inDebugMode()) {
                prestigeFrame = new JFrame("Prestige (DEBUG)");
            } else {
                prestigeFrame = new JFrame("Prestige");
            }
            prestigePane = new JPanel();
            prestigePane.setLayout(new BorderLayout());
            contentPane = new JPanel();
            contentPane.setLayout(new GridLayout(
                    PrestigeConstants.MAX_PRESTIGE + 1, 1));
            buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout());
            btnOK = new JButton("OK");
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    prestigeFrame.setVisible(false);
                }
            });
            prestigeValues = new JLabel[PrestigeConstants.MAX_PRESTIGE + 1];
            for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE + 1; x++) {
                prestigeValues[x] = new JLabel();
                contentPane.add(prestigeValues[x]);
            }
            buttonPane.add(btnOK);
            prestigePane.add(contentPane, BorderLayout.CENTER);
            prestigePane.add(buttonPane, BorderLayout.SOUTH);
            prestigeFrame.setContentPane(prestigePane);
            inited = true;
        }
    }
}
