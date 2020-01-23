/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.maze.Maze;
import com.puttysoftware.mazemode.resourcemanagers.GraphicsManager;

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
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        this.hpLabel.setText(m.getHPString());
        this.poisonLabel.setText(m.getPoisonString());
        this.timeLabel.setText(m.getTimeString());
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(3, 1));
        final BufferedImageIcon hpImage = GraphicsManager
                .getStatImage("health");
        this.hpLabel = new JLabel("", hpImage, SwingConstants.LEFT);
        this.statsPane.add(this.hpLabel);
        final BufferedImageIcon poisonImage = GraphicsManager
                .getStatImage("poison");
        this.poisonLabel = new JLabel("", poisonImage, SwingConstants.LEFT);
        this.statsPane.add(this.poisonLabel);
        final BufferedImageIcon timeImage = GraphicsManager
                .getStatImage("time");
        this.timeLabel = new JLabel("", timeImage, SwingConstants.LEFT);
        this.statsPane.add(this.timeLabel);
    }

    public void updateImages() {
        final BufferedImageIcon hpImage = GraphicsManager
                .getStatImage("health");
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon poisonImage = GraphicsManager
                .getStatImage("poison");
        this.poisonLabel.setIcon(poisonImage);
        final BufferedImageIcon timeImage = GraphicsManager
                .getStatImage("time");
        this.timeLabel.setIcon(timeImage);
    }
}
