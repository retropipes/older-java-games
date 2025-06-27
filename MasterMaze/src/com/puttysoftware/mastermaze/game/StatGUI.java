/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.PartyMember;
import com.puttysoftware.mastermaze.maze.Maze;
import com.puttysoftware.mastermaze.resourcemanagers.StatImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.StatImageManager;

class StatGUI {
        // Fields
        private Container statsPane;
        private JLabel hpLabel;
        private JLabel mpLabel;
        private JLabel goldLabel;
        private JLabel attackLabel;
        private JLabel defenseLabel;
        private JLabel xpLabel;
        private JLabel poisonLabel;
        private JLabel timeLabel;

        // Constructors
        StatGUI() {
                this.setUpGUI();
        }

        // Methods
        Container getStatsPane() {
                return this.statsPane;
        }

        void updateStats() {
                final PartyMember pc = PartyManager.getParty().getLeader();
                final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
                this.hpLabel.setText(pc.getHPString());
                this.mpLabel.setText(pc.getMPString());
                this.goldLabel.setText(Integer.toString(pc.getGold()));
                this.attackLabel.setText(Integer.toString(pc.getAttack()));
                this.defenseLabel.setText(Integer.toString(pc.getDefense()));
                this.xpLabel.setText(pc.getXPString());
                this.poisonLabel.setText(m.getPoisonString());
                this.timeLabel.setText(m.getTimeString());
        }

        private void setUpGUI() {
                this.statsPane = new Container();
                this.statsPane.setLayout(new GridLayout(8, 1));
                this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.goldLabel = new JLabel("", null, SwingConstants.LEFT);
                this.attackLabel = new JLabel("", null, SwingConstants.LEFT);
                this.defenseLabel = new JLabel("", null, SwingConstants.LEFT);
                this.xpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.poisonLabel = new JLabel("", null, SwingConstants.LEFT);
                this.timeLabel = new JLabel("", null, SwingConstants.LEFT);
                this.statsPane.add(this.hpLabel);
                this.statsPane.add(this.mpLabel);
                this.statsPane.add(this.goldLabel);
                this.statsPane.add(this.attackLabel);
                this.statsPane.add(this.defenseLabel);
                this.statsPane.add(this.xpLabel);
                this.statsPane.add(this.poisonLabel);
                this.statsPane.add(this.timeLabel);
        }

        void updateImages() {
                final BufferedImageIcon hpImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_HEALTH);
                this.hpLabel.setIcon(hpImage);
                final BufferedImageIcon mpImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_MAGIC);
                this.mpLabel.setIcon(mpImage);
                final BufferedImageIcon goldImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_GOLD);
                this.goldLabel.setIcon(goldImage);
                final BufferedImageIcon attackImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_ATTACK);
                this.attackLabel.setIcon(attackImage);
                final BufferedImageIcon defenseImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_DEFENSE);
                this.defenseLabel.setIcon(defenseImage);
                final BufferedImageIcon xpImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_XP);
                this.xpLabel.setIcon(xpImage);
                final BufferedImageIcon poisonImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_POISON);
                this.poisonLabel.setIcon(poisonImage);
                final BufferedImageIcon timeImage = StatImageManager
                                .getImage(StatImageConstants.STAT_IMAGE_TIME);
                this.timeLabel.setIcon(timeImage);
        }
}
