/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.PartyMember;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.names.NamesConstants;
import com.puttysoftware.gemma.support.names.NamesManager;

public class StatisticsViewer {
    // Fields
    static JFrame statisticsFrame;
    private static JPanel statisticsPane;
    private static JPanel contentPane;
    private static JPanel buttonPane;
    private static JButton btnOK;
    private static JLabel[] statisticsValues;
    private static boolean inited = false;

    // Private Constructor
    private StatisticsViewer() {
        // Do nothing
    }

    // Methods
    public static void viewStatistics() {
        setUpGUI();
        PartyMember leader = PartyManager.getParty().getLeader();
        if (leader != null) {
            for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
                long value = leader.getStat(x);
                if (x == StatConstants.STAT_HIT
                        || x == StatConstants.STAT_EVADE) {
                    double fmtVal = value / 100.0;
                    statisticsValues[x].setText(" "
                            + NamesManager.getName(NamesConstants.SECTION_STATS,
                                    NamesConstants.SECTION_ARRAY_STATS[x])
                            + ": " + fmtVal + "%  ");
                } else {
                    statisticsValues[x].setText(" "
                            + NamesManager.getName(NamesConstants.SECTION_STATS,
                                    NamesConstants.SECTION_ARRAY_STATS[x])
                            + ": " + value + "  ");
                }
            }
            statisticsFrame.pack();
            statisticsFrame.setVisible(true);
        } else {
            CommonDialogs.showDialog("Nothing to display");
        }
    }

    private static void setUpGUI() {
        if (!inited) {
            if (Support.inDebugMode()) {
                statisticsFrame = new JFrame("Statistics (DEBUG)");
            } else {
                statisticsFrame = new JFrame("Statistics");
            }
            statisticsPane = new JPanel();
            statisticsPane.setLayout(new BorderLayout());
            contentPane = new JPanel();
            contentPane.setLayout(new GridLayout(StatConstants.MAX_STATS, 1));
            buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout());
            btnOK = new JButton("OK");
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    statisticsFrame.setVisible(false);
                }
            });
            statisticsValues = new JLabel[StatConstants.MAX_DISPLAY_STATS];
            for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
                statisticsValues[x] = new JLabel();
                contentPane.add(statisticsValues[x]);
            }
            buttonPane.add(btnOK);
            statisticsPane.add(contentPane, BorderLayout.CENTER);
            statisticsPane.add(buttonPane, BorderLayout.SOUTH);
            statisticsFrame.setContentPane(statisticsPane);
            inited = true;
        }
    }
}
