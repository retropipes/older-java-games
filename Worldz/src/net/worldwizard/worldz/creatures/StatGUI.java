/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.creatures;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.world.World;

public class StatGUI {
    // Fields
    private Container statsPane;
    private JLabel poisonLabel;
    private JLabel hpLabel;
    private JLabel mpLabel;
    private JLabel goldLabel;
    private JLabel attackLabel;
    private JLabel defenseLabel;
    private JLabel xpLabel;

    // Constructors
    public StatGUI() {
        this.setUpGUI();
    }

    // Methods
    public Container getStatsPane() {
        return this.statsPane;
    }

    public void updateStats() {
        final World w = Worldz.getApplication().getWorldManager().getWorld();
        final PartyMember pc = PartyManager.getParty().getLeader();
        this.poisonLabel.setText(w.getPoisonString());
        this.hpLabel.setText(pc.getHPString());
        this.mpLabel.setText(pc.getMPString());
        this.goldLabel.setText(Integer.toString(pc.getGold()));
        this.attackLabel.setText(Integer.toString(pc.getAttack()));
        this.defenseLabel.setText(Integer.toString(pc.getDefense()));
        this.xpLabel.setText(pc.getXPString());
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(7, 1));
        this.poisonLabel = new JLabel("", null, SwingConstants.LEFT);
        this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.goldLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attackLabel = new JLabel("", null, SwingConstants.LEFT);
        this.defenseLabel = new JLabel("", null, SwingConstants.LEFT);
        this.xpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.statsPane.add(this.poisonLabel);
        this.statsPane.add(this.hpLabel);
        this.statsPane.add(this.mpLabel);
        this.statsPane.add(this.goldLabel);
        this.statsPane.add(this.attackLabel);
        this.statsPane.add(this.defenseLabel);
        this.statsPane.add(this.xpLabel);
    }

    public void updateGUI() {
        final BufferedImageIcon poisonImage = GraphicsManager
                .getStatImage("poison");
        this.poisonLabel.setIcon(poisonImage);
        final BufferedImageIcon hpImage = GraphicsManager
                .getStatImage("health");
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon mpImage = GraphicsManager.getStatImage("magic");
        this.mpLabel.setIcon(mpImage);
        final BufferedImageIcon goldImage = GraphicsManager
                .getStatImage("gold");
        this.goldLabel.setIcon(goldImage);
        final BufferedImageIcon attackImage = GraphicsManager
                .getStatImage("attack");
        this.attackLabel.setIcon(attackImage);
        final BufferedImageIcon defenseImage = GraphicsManager
                .getStatImage("defense");
        this.defenseLabel.setIcon(defenseImage);
        final BufferedImageIcon xpImage = GraphicsManager.getStatImage("xp");
        this.xpLabel.setIcon(xpImage);
    }
}
