/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.battle;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.map.objects.BattleCharacter;
import net.worldwizard.support.resourcemanagers.StatImageManager;

class BattleStats {
        // Fields
        private Container statsPane;
        private JLabel nameLabel;
        private JLabel teamLabel;
        private JLabel hpLabel;
        private JLabel mpLabel;
        private JLabel attLabel;
        private JLabel defLabel;
        private JLabel apLabel;
        private JLabel attLeftLabel;
        private JLabel splLabel;

        // Constructors
        public BattleStats() {
                this.setUpGUI();
                this.updateIcons();
        }

        // Methods
        public Container getStatsPane() {
                return this.statsPane;
        }

        public void updateStats(final BattleCharacter bc) {
                this.nameLabel.setText(bc.getName());
                this.teamLabel.setText(bc.getTeamString());
                this.hpLabel.setText(bc.getTemplate().getHPString());
                this.mpLabel.setText(bc.getTemplate().getMPString());
                this.attLabel.setText(bc.getTemplate().getAttackString());
                this.defLabel.setText(bc.getTemplate().getDefenseString());
                this.apLabel.setText(bc.getAPString());
                this.attLeftLabel.setText(bc.getAttackString());
                this.splLabel.setText(bc.getSpellString());
        }

        private void setUpGUI() {
                this.statsPane = new Container();
                this.statsPane.setLayout(new GridLayout(9, 1));
                this.nameLabel = new JLabel("", null, SwingConstants.LEFT);
                this.teamLabel = new JLabel("", null, SwingConstants.LEFT);
                this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.attLabel = new JLabel("", null, SwingConstants.LEFT);
                this.defLabel = new JLabel("", null, SwingConstants.LEFT);
                this.apLabel = new JLabel("", null, SwingConstants.LEFT);
                this.attLeftLabel = new JLabel("", null, SwingConstants.LEFT);
                this.splLabel = new JLabel("", null, SwingConstants.LEFT);
                this.statsPane.add(this.nameLabel);
                this.statsPane.add(this.teamLabel);
                this.statsPane.add(this.hpLabel);
                this.statsPane.add(this.mpLabel);
                this.statsPane.add(this.attLabel);
                this.statsPane.add(this.defLabel);
                this.statsPane.add(this.apLabel);
                this.statsPane.add(this.attLeftLabel);
                this.statsPane.add(this.splLabel);
        }

        private void updateIcons() {
                final BufferedImageIcon nameImage = StatImageManager
                                .getStatImage("name");
                this.nameLabel.setIcon(nameImage);
                final BufferedImageIcon teamImage = StatImageManager
                                .getStatImage("team");
                this.teamLabel.setIcon(teamImage);
                final BufferedImageIcon hpImage = StatImageManager
                                .getStatImage("health");
                this.hpLabel.setIcon(hpImage);
                final BufferedImageIcon mpImage = StatImageManager
                                .getStatImage("magic");
                this.mpLabel.setIcon(mpImage);
                final BufferedImageIcon attImage = StatImageManager
                                .getStatImage("attack");
                this.attLabel.setIcon(attImage);
                final BufferedImageIcon defImage = StatImageManager
                                .getStatImage("defense");
                this.defLabel.setIcon(defImage);
                final BufferedImageIcon apImage = StatImageManager
                                .getStatImage("actions");
                this.apLabel.setIcon(apImage);
                this.attLeftLabel.setIcon(attImage);
                final BufferedImageIcon spImage = StatImageManager
                                .getStatImage("spells");
                this.splLabel.setIcon(spImage);
        }
}
