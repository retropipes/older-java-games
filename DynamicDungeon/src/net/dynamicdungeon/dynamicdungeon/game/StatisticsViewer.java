/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.creatures.StatConstants;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyMember;
import net.dynamicdungeon.dynamicdungeon.names.NamesConstants;
import net.dynamicdungeon.dynamicdungeon.names.NamesManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;

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
	final PartyMember leader = PartyManager.getParty().getLeader();
	if (leader != null) {
	    for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
		final long value = leader.getStat(x);
		if (x == StatConstants.STAT_HIT
			|| x == StatConstants.STAT_EVADE) {
		    final double fmtVal = value / 100.0;
		    statisticsValues[x].setText(" "
			    + NamesManager.getName(
				    NamesConstants.SECTION_STATS,
				    NamesConstants.SECTION_ARRAY_STATS[x])
			    + ": " + fmtVal + "%  ");
		} else {
		    statisticsValues[x].setText(" "
			    + NamesManager.getName(
				    NamesConstants.SECTION_STATS,
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
	    statisticsFrame = new JFrame("Statistics");
	    final Image iconlogo = LogoManager.getIconLogo();
	    statisticsFrame.setIconImage(iconlogo);
	    statisticsPane = new JPanel();
	    statisticsPane.setLayout(new BorderLayout());
	    contentPane = new JPanel();
	    contentPane.setLayout(new GridLayout(StatConstants.MAX_STATS, 1));
	    buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout());
	    btnOK = new JButton("OK");
	    btnOK.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
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
