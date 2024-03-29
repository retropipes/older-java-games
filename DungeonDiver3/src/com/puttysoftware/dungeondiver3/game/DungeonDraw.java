package com.puttysoftware.dungeondiver3.game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.puttysoftware.dungeondiver3.DrawGrid;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;

class DungeonDraw extends JPanel {
    private static final long serialVersionUID = 35935343464625L;
    private DrawGrid drawGrid;

    public DungeonDraw() {
        super();
        final int vSize = GameViewingWindowManager.getViewingWindowSize();
        final int gSize = ImageManager.getGraphicSize();
        this.setPreferredSize(new Dimension(vSize * gSize, vSize * gSize));
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.drawGrid != null) {
            final int gSize = ImageManager.getGraphicSize();
            final int vSize = GameViewingWindowManager.getViewingWindowSize();
            for (int x = 0; x < vSize; x++) {
                for (int y = 0; y < vSize; y++) {
                    g.drawImage(this.drawGrid.getImageCell(y, x), x * gSize,
                            y * gSize, gSize, gSize, null);
                }
            }
        }
    }

    public void updateGrid(final DrawGrid newGrid) {
        this.drawGrid = newGrid;
    }
}
