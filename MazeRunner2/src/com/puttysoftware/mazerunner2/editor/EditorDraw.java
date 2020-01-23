package com.puttysoftware.mazerunner2.editor;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.puttysoftware.mazerunner2.DrawGrid;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.mazerunner2.resourcemanagers.ImageTransformer;

class EditorDraw extends JPanel {
    private static final long serialVersionUID = 35935343464625L;
    private DrawGrid drawGrid;

    public EditorDraw(DrawGrid grid) {
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
