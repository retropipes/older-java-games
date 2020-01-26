package net.dynamicdungeon.dynamicdungeon;

import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.llds.BasicLowLevelDataStore;

public class DrawGrid extends BasicLowLevelDataStore {
    public DrawGrid(final int numSquares) {
        super(numSquares, numSquares);
    }

    public BufferedImageIcon getImageCell(final int row, final int col) {
        return (BufferedImageIcon) this.getCell(row, col);
    }

    public void setImageCell(final BufferedImageIcon bii, final int row,
            final int col) {
        this.setCell(bii, row, col);
    }
}
