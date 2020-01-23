package com.puttysoftware.dungeondiver4.game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.puttysoftware.dungeondiver4.DrawGrid;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.ImageTransformer;

class GameDraw extends JPanel {
    private static final long serialVersionUID = 35935343464625L;
    private DrawGrid drawGrid;

    public GameDraw(DrawGrid grid) {
        super();
        this.drawGrid = grid;
        int vSize = PreferencesManager.getViewingWindowSize();
        int gSize = ImageTransformer.getGraphicSize();
        this.setPreferredSize(new Dimension(vSize * gSize, vSize * gSize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.drawGrid != null) {
            int gSize = ImageTransformer.getGraphicSize();
            int vSize = PreferencesManager.getViewingWindowSize();
            for (int x = 0; x < vSize; x++) {
                for (int y = 0; y < vSize; y++) {
                    g.drawImage(this.drawGrid.getImageCell(y, x), x * gSize, y
                            * gSize, gSize, gSize, null);
                }
            }
        }
    }
}
