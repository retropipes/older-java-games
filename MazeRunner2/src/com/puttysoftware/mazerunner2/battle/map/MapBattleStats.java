/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.battle.map;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazerunner2.maze.objects.BattleCharacter;
import com.puttysoftware.mazerunner2.resourcemanagers.StatImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.StatImageManager;

class MapBattleStats {
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
    public MapBattleStats() {
        this.setUpGUI();
        this.updateIcons();
    }

    // Methods
    public Container getStatsPane() {
        return this.statsPane;
    }

    public void updateStats(BattleCharacter bc) {
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
        BufferedImageIcon nameImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_NAME);
        this.nameLabel.setIcon(nameImage);
        BufferedImageIcon teamImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_TEAM);
        this.teamLabel.setIcon(teamImage);
        BufferedImageIcon hpImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_HEALTH);
        this.hpLabel.setIcon(hpImage);
        BufferedImageIcon mpImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_MAGIC);
        this.mpLabel.setIcon(mpImage);
        BufferedImageIcon attImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_ATTACK);
        this.attLabel.setIcon(attImage);
        BufferedImageIcon defImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_DEFENSE);
        this.defLabel.setIcon(defImage);
        BufferedImageIcon apImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_ACTIONS);
        this.apLabel.setIcon(apImage);
        BufferedImageIcon attLeftImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_ATTACKS);
        this.attLeftLabel.setIcon(attLeftImage);
        BufferedImageIcon spImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_SPELLS);
        this.splLabel.setIcon(spImage);
    }
}
