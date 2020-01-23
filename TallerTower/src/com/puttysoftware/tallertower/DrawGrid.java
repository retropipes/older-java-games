package com.puttysoftware.tallertower;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.llds.BasicLowLevelDataStore;

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
