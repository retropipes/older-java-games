/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.riskyrescue.assets.ImageManager;
import com.puttysoftware.riskyrescue.assets.StatImage;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.creatures.party.PartyMember;

class StatGUI {
        // Fields
        private Container statsPane;
        private JLabel hpLabel;
        private JLabel mpLabel;
        private JLabel goldLabel;
        private JLabel attackLabel;
        private JLabel defenseLabel;
        private JLabel xpLabel;
        private JLabel dlLabel;

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
                this.hpLabel.setText(pc.getHPString());
                this.mpLabel.setText(pc.getMPString());
                this.goldLabel.setText(Integer.toString(pc.getGold()));
                this.attackLabel.setText(Integer.toString(pc.getAttack()));
                this.defenseLabel.setText(Integer.toString(pc.getDefense()));
                this.xpLabel.setText(pc.getXPString());
                this.dlLabel.setText(PartyManager.getDungeonLevelString());
        }

        private void setUpGUI() {
                this.statsPane = new Container();
                this.statsPane.setLayout(new GridLayout(7, 1));
                this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.goldLabel = new JLabel("", null, SwingConstants.LEFT);
                this.attackLabel = new JLabel("", null, SwingConstants.LEFT);
                this.defenseLabel = new JLabel("", null, SwingConstants.LEFT);
                this.xpLabel = new JLabel("", null, SwingConstants.LEFT);
                this.dlLabel = new JLabel("", null, SwingConstants.LEFT);
                this.statsPane.add(this.hpLabel);
                this.statsPane.add(this.mpLabel);
                this.statsPane.add(this.goldLabel);
                this.statsPane.add(this.attackLabel);
                this.statsPane.add(this.defenseLabel);
                this.statsPane.add(this.xpLabel);
                this.statsPane.add(this.dlLabel);
        }

        void updateImages() {
                final BufferedImageIcon hpImage = ImageManager
                                .getStatImage(StatImage.HEALTH);
                this.hpLabel.setIcon(hpImage);
                final BufferedImageIcon mpImage = ImageManager
                                .getStatImage(StatImage.MAGIC);
                this.mpLabel.setIcon(mpImage);
                final BufferedImageIcon goldImage = ImageManager
                                .getStatImage(StatImage.MONEY);
                this.goldLabel.setIcon(goldImage);
                final BufferedImageIcon attackImage = ImageManager
                                .getStatImage(StatImage.MELEE_ATTACK);
                this.attackLabel.setIcon(attackImage);
                final BufferedImageIcon defenseImage = ImageManager
                                .getStatImage(StatImage.DEFENSE);
                this.defenseLabel.setIcon(defenseImage);
                final BufferedImageIcon xpImage = ImageManager
                                .getStatImage(StatImage.EXPERIENCE);
                this.xpLabel.setIcon(xpImage);
                final BufferedImageIcon dlImage = ImageManager
                                .getStatImage(StatImage.DUNGEON_LEVEL);
                this.dlLabel.setIcon(dlImage);
        }
}
