/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.creatures.party.PartyMember;
import com.puttysoftware.fantastlex.names.NamesConstants;
import com.puttysoftware.fantastlex.names.NamesManager;

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
        StatisticsViewer.setUpGUI();
        final PartyMember leader = PartyManager.getParty().pickOnePartyMember();
        if (leader != null) {
            for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
                final long value = leader.getStat(x);
                if (x == StatConstants.STAT_HIT
                        || x == StatConstants.STAT_EVADE) {
                    final double fmtVal = value / 100.0;
                    StatisticsViewer.statisticsValues[x].setText(" "
                            + NamesManager.getName(
                                    NamesConstants.SECTION_STATS,
                                    NamesConstants.SECTION_ARRAY_STATS[x])
                            + ": " + fmtVal + "%  ");
                } else {
                    StatisticsViewer.statisticsValues[x].setText(" "
                            + NamesManager.getName(
                                    NamesConstants.SECTION_STATS,
                                    NamesConstants.SECTION_ARRAY_STATS[x])
                            + ": " + value + "  ");
                }
            }
            StatisticsViewer.statisticsFrame.pack();
            StatisticsViewer.statisticsFrame.setVisible(true);
        } else {
            CommonDialogs.showDialog("Nothing to display");
        }
    }

    private static void setUpGUI() {
        if (!StatisticsViewer.inited) {
            if (FantastleX.inDebugMode()) {
                StatisticsViewer.statisticsFrame = new JFrame(
                        "Statistics (DEBUG)");
            } else {
                StatisticsViewer.statisticsFrame = new JFrame("Statistics");
            }
            StatisticsViewer.statisticsPane = new JPanel();
            StatisticsViewer.statisticsPane.setLayout(new BorderLayout());
            StatisticsViewer.contentPane = new JPanel();
            StatisticsViewer.contentPane.setLayout(new GridLayout(
                    StatConstants.MAX_STATS, 1));
            StatisticsViewer.buttonPane = new JPanel();
            StatisticsViewer.buttonPane.setLayout(new FlowLayout());
            StatisticsViewer.btnOK = new JButton("OK");
            StatisticsViewer.btnOK
                    .addActionListener(e -> StatisticsViewer.statisticsFrame
                            .setVisible(false));
            StatisticsViewer.statisticsValues = new JLabel[StatConstants.MAX_DISPLAY_STATS];
            for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
                StatisticsViewer.statisticsValues[x] = new JLabel();
                StatisticsViewer.contentPane
                        .add(StatisticsViewer.statisticsValues[x]);
            }
            StatisticsViewer.buttonPane.add(StatisticsViewer.btnOK);
            StatisticsViewer.statisticsPane.add(StatisticsViewer.contentPane,
                    BorderLayout.CENTER);
            StatisticsViewer.statisticsPane.add(StatisticsViewer.buttonPane,
                    BorderLayout.SOUTH);
            StatisticsViewer.statisticsFrame
                    .setContentPane(StatisticsViewer.statisticsPane);
            StatisticsViewer.inited = true;
        }
    }
}
