/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.game;

public class GameViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE_X = 13;
    private static final int VIEWING_WINDOW_SIZE_Y = 13;

    // Constructors
    public GameViewingWindowManager() {
        this.locX = 0;
        this.locY = 0;
        this.oldLocX = 0;
        this.oldLocY = 0;
    }

    // Methods
    public int getViewingWindowLocationX() {
        return this.locX;
    }

    public int getViewingWindowLocationY() {
        return this.locY;
    }

    public int getLowerRightViewingWindowLocationX() {
        return this.locX + GameViewingWindowManager.VIEWING_WINDOW_SIZE_X - 1;
    }

    public int getLowerRightViewingWindowLocationY() {
        return this.locY + GameViewingWindowManager.VIEWING_WINDOW_SIZE_Y - 1;
    }

    public void setViewingWindowLocationX(final int val) {
        this.locX = val;
    }

    public void setViewingWindowLocationY(final int val) {
        this.locY = val;
    }

    public void offsetViewingWindowLocationX(final int val) {
        this.locX += val;
    }

    public void offsetViewingWindowLocationY(final int val) {
        this.locY += val;
    }

    public void saveViewingWindow() {
        this.oldLocX = this.locX;
        this.oldLocY = this.locY;
    }

    public void restoreViewingWindow() {
        this.locX = this.oldLocX;
        this.locY = this.oldLocY;
    }

    public int getViewingWindowSizeX() {
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE_X;
    }

    public int getViewingWindowSizeY() {
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE_Y;
    }

    public int getOffsetFactorX() {
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE_X / 2;
    }

    public int getOffsetFactorY() {
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE_Y / 2;
    }
}
