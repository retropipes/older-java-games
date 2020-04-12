/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.EffectImageIndex;
import com.puttysoftware.mazer5d.compatibility.maze.Maze;
import com.puttysoftware.mazer5d.loaders.EffectImageLoader;

class StatGUI {
    // Fields
    private Container statsPane;
    private JLabel hpLabel;
    private JLabel poisonLabel;
    private JLabel timeLabel;

    // Constructors
    public StatGUI() {
        this.setUpGUI();
    }

    // Methods
    public Container getStatsPane() {
        return this.statsPane;
    }

    public void updateStats() {
        final Maze m = Mazer5D.getApplication().getMazeManager().getMaze();
        this.hpLabel.setText(m.getHPString());
        this.poisonLabel.setText(m.getPoisonString());
        this.timeLabel.setText(m.getTimeString());
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(3, 1));
        final BufferedImageIcon hpImage = EffectImageLoader.load(
                EffectImageIndex.HEALTH);
        this.hpLabel = new JLabel("", hpImage, SwingConstants.LEFT);
        this.statsPane.add(this.hpLabel);
        final BufferedImageIcon poisonImage = EffectImageLoader.load(
                EffectImageIndex.POISON);
        this.poisonLabel = new JLabel("", poisonImage, SwingConstants.LEFT);
        this.statsPane.add(this.poisonLabel);
        final BufferedImageIcon timeImage = EffectImageLoader.load(
                EffectImageIndex.TIME_19);
        this.timeLabel = new JLabel("", timeImage, SwingConstants.LEFT);
        this.statsPane.add(this.timeLabel);
    }

    public void updateImages() {
        final BufferedImageIcon hpImage = EffectImageLoader.load(
                EffectImageIndex.HEALTH);
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon poisonImage = EffectImageLoader.load(
                EffectImageIndex.POISON);
        this.poisonLabel.setIcon(poisonImage);
        final BufferedImageIcon timeImage = EffectImageLoader.load(
                EffectImageIndex.TIME_19);
        this.timeLabel.setIcon(timeImage);
    }
}
