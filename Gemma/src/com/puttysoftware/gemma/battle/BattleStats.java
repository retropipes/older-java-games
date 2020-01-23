/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.images.BufferedImageIcon;

class BattleStats {
    // Fields
    private Container statsPane;
    private JLabel nameLabel;
    private JLabel teamLabel;
    private JLabel hpLabel;
    private JLabel mpLabel;
    private JLabel attLabel;
    private JLabel defLabel;
    private JLabel magattLabel;
    private JLabel magdefLabel;
    private JLabel apLabel;
    private JLabel attLeftLabel;
    private JLabel splLabel;
    private JLabel itmLabel;
    private JLabel stlLabel;

    // Constructors
    public BattleStats() {
        this.setUpGUI();
        this.updateIcons();
    }

    // Methods
    public Container getStatsPane() {
        return this.statsPane;
    }

    public void updateStats(BattleCharacter bc) {
        if (bc != null) {
            this.nameLabel.setText(bc.getName());
            this.teamLabel.setText(bc.getTeamString());
            this.hpLabel.setText(bc.getTemplate().getHPString());
            this.mpLabel.setText(bc.getTemplate().getMPString());
            this.attLabel.setText(bc.getTemplate().getAttackString());
            this.defLabel.setText(bc.getTemplate().getDefenseString());
            this.magattLabel.setText(bc.getTemplate().getMagicPowerString());
            this.magdefLabel.setText(bc.getTemplate().getMagicDefenseString());
            this.apLabel.setText(bc.getAPString());
            this.attLeftLabel.setText(bc.getAttackString());
            this.splLabel.setText(bc.getSpellString());
            this.itmLabel.setText(bc.getItemString());
            this.stlLabel.setText(bc.getStealString());
        }
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(13, 1));
        this.nameLabel = new JLabel("", null, SwingConstants.LEFT);
        this.teamLabel = new JLabel("", null, SwingConstants.LEFT);
        this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attLabel = new JLabel("", null, SwingConstants.LEFT);
        this.defLabel = new JLabel("", null, SwingConstants.LEFT);
        this.magattLabel = new JLabel("", null, SwingConstants.LEFT);
        this.magdefLabel = new JLabel("", null, SwingConstants.LEFT);
        this.apLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attLeftLabel = new JLabel("", null, SwingConstants.LEFT);
        this.splLabel = new JLabel("", null, SwingConstants.LEFT);
        this.itmLabel = new JLabel("", null, SwingConstants.LEFT);
        this.stlLabel = new JLabel("", null, SwingConstants.LEFT);
        this.statsPane.add(this.nameLabel);
        this.statsPane.add(this.teamLabel);
        this.statsPane.add(this.hpLabel);
        this.statsPane.add(this.mpLabel);
        this.statsPane.add(this.attLabel);
        this.statsPane.add(this.defLabel);
        this.statsPane.add(this.magattLabel);
        this.statsPane.add(this.magdefLabel);
        this.statsPane.add(this.apLabel);
        this.statsPane.add(this.attLeftLabel);
        this.statsPane.add(this.splLabel);
        this.statsPane.add(this.itmLabel);
        this.statsPane.add(this.stlLabel);
    }

    private void updateIcons() {
        BufferedImageIcon nameImage = ImageManager.getStatImage("name");
        this.nameLabel.setIcon(nameImage);
        BufferedImageIcon teamImage = ImageManager.getStatImage("team");
        this.teamLabel.setIcon(teamImage);
        BufferedImageIcon hpImage = ImageManager.getStatImage("health");
        this.hpLabel.setIcon(hpImage);
        BufferedImageIcon mpImage = ImageManager.getStatImage("magic");
        this.mpLabel.setIcon(mpImage);
        BufferedImageIcon attImage = ImageManager.getStatImage("attack");
        this.attLabel.setIcon(attImage);
        BufferedImageIcon defImage = ImageManager.getStatImage("defense");
        this.defLabel.setIcon(defImage);
        BufferedImageIcon magattImage = ImageManager
                .getStatImage("magic_power");
        this.magattLabel.setIcon(magattImage);
        BufferedImageIcon magdefImage = ImageManager
                .getStatImage("magic_defense");
        this.magdefLabel.setIcon(magdefImage);
        BufferedImageIcon apImage = ImageManager.getStatImage("actions");
        this.apLabel.setIcon(apImage);
        BufferedImageIcon attLeftImage = ImageManager.getStatImage("attacks");
        this.attLeftLabel.setIcon(attLeftImage);
        BufferedImageIcon spImage = ImageManager.getStatImage("spells");
        this.splLabel.setIcon(spImage);
        BufferedImageIcon itImage = ImageManager.getStatImage("items");
        this.itmLabel.setIcon(itImage);
        BufferedImageIcon stImage = ImageManager.getStatImage("steals");
        this.stlLabel.setIcon(stImage);
    }
}
