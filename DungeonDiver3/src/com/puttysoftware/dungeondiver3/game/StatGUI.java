/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.dungeondiver3.support.creatures.PartyMember;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
import com.puttysoftware.images.BufferedImageIcon;

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
        PartyMember pc = PartyManager.getParty().getLeader();
        this.hpLabel.setText(pc.getHPString());
        this.mpLabel.setText(pc.getMPString());
        this.goldLabel.setText(Integer.toString(pc.getGold()));
        this.attackLabel.setText(Integer.toString(pc.getAttack()));
        this.defenseLabel.setText(Integer.toString(pc.getDefense()));
        this.xpLabel.setText(pc.getXPString());
        this.dlLabel.setText(PartyManager.getParty().getDungeonLevelString());
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
        BufferedImageIcon hpImage = ImageManager.getStatImage("health");
        this.hpLabel.setIcon(hpImage);
        BufferedImageIcon mpImage = ImageManager.getStatImage("magic");
        this.mpLabel.setIcon(mpImage);
        BufferedImageIcon goldImage = ImageManager.getStatImage("gold");
        this.goldLabel.setIcon(goldImage);
        BufferedImageIcon attackImage = ImageManager.getStatImage("attack");
        this.attackLabel.setIcon(attackImage);
        BufferedImageIcon defenseImage = ImageManager.getStatImage("defense");
        this.defenseLabel.setIcon(defenseImage);
        BufferedImageIcon xpImage = ImageManager.getStatImage("xp");
        this.xpLabel.setIcon(xpImage);
        BufferedImageIcon dlImage = ImageManager.getStatImage("ml");
        this.dlLabel.setIcon(dlImage);
    }
}
