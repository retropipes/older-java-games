/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.chrystalz.manager.string.LocalizedFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

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
        final PartyMember leader = PartyManager.getParty().getLeader();
        if (leader != null) {
            for (int x = 0; x < StatConstants.MAX_DISPLAY_STATS; x++) {
                final long value = leader.getStat(x);
                if (x == StatConstants.STAT_HIT
                        || x == StatConstants.STAT_EVADE) {
                    final double fmtVal = value / 100.0;
                    StatisticsViewer.statisticsValues[x].setText(" "
                            + StringManager
                                    .getLocalizedString(LocalizedFile.STATS, x)
                            + ": " + fmtVal + "%  ");
                } else {
                    StatisticsViewer.statisticsValues[x].setText(" "
                            + StringManager
                                    .getLocalizedString(LocalizedFile.STATS, x)
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
            StatisticsViewer.statisticsFrame = new JFrame("Statistics");
            final Image iconlogo = LogoManager.getIconLogo();
            StatisticsViewer.statisticsFrame.setIconImage(iconlogo);
            StatisticsViewer.statisticsPane = new JPanel();
            StatisticsViewer.statisticsPane.setLayout(new BorderLayout());
            StatisticsViewer.contentPane = new JPanel();
            StatisticsViewer.contentPane
                    .setLayout(new GridLayout(StatConstants.MAX_STATS, 1));
            StatisticsViewer.buttonPane = new JPanel();
            StatisticsViewer.buttonPane.setLayout(new FlowLayout());
            StatisticsViewer.btnOK = new JButton("OK");
            StatisticsViewer.btnOK.addActionListener(
                    e -> StatisticsViewer.statisticsFrame.setVisible(false));
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
