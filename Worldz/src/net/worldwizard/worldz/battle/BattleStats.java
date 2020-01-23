/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.battle;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.objects.BattleCharacter;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;

class BattleStats {
    // Fields
    private Container statsPane;
    private JLabel nameLabel;
    private JLabel hpLabel;
    private JLabel mpLabel;
    private JLabel apLabel;
    private JLabel attLabel;
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
        this.hpLabel.setText(bc.getTemplate().getHPString());
        this.mpLabel.setText(bc.getTemplate().getMPString());
        this.apLabel.setText(bc.getAPString());
        this.attLabel.setText(bc.getAttackString());
        this.splLabel.setText(bc.getSpellString());
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(6, 1));
        this.nameLabel = new JLabel("", null, SwingConstants.LEFT);
        this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.apLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attLabel = new JLabel("", null, SwingConstants.LEFT);
        this.splLabel = new JLabel("", null, SwingConstants.LEFT);
        this.statsPane.add(this.nameLabel);
        this.statsPane.add(this.hpLabel);
        this.statsPane.add(this.mpLabel);
        this.statsPane.add(this.apLabel);
        this.statsPane.add(this.attLabel);
        this.statsPane.add(this.splLabel);
    }

    private void updateIcons() {
        final BufferedImageIcon hpImage = GraphicsManager
                .getStatImage("health");
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon mpImage = GraphicsManager.getStatImage("magic");
        this.mpLabel.setIcon(mpImage);
    }
}
