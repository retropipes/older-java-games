package com.puttysoftware.mazerunner2;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.llds.BasicLowLevelDataStore;

public class DrawGrid extends BasicLowLevelDataStore {
    public DrawGrid(int numSquares) {
        super(numSquares, numSquares);
    }

    public BufferedImageIcon getImageCell(int row, int col) {
        return (BufferedImageIcon) this.getCell(row, col);
    }

    public void setImageCell(BufferedImageIcon bii, int row, int col) {
        this.setCell(bii, row, col);
    }
}
