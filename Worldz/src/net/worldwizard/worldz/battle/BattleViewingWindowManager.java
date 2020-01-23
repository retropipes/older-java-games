/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.battle;

class BattleViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE_X = 13;
    private static final int VIEWING_WINDOW_SIZE_Y = 13;

    // Constructors
    public BattleViewingWindowManager() {
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

    public int getUpperLeftViewingWindowLocationX() {
        return this.locX;
    }

    public int getUpperLeftViewingWindowLocationY() {
        return this.locY;
    }

    public int getLowerRightViewingWindowLocationX() {
        return this.locX + BattleViewingWindowManager.VIEWING_WINDOW_SIZE_X - 1;
    }

    public int getLowerRightViewingWindowLocationY() {
        return this.locY + BattleViewingWindowManager.VIEWING_WINDOW_SIZE_Y - 1;
    }

    public void setViewingWindowLocationX(final int val) {
        this.locX = val;
    }

    public void setViewingWindowLocationY(final int val) {
        this.locY = val;
    }

    public void setViewingWindowCenterX(final int val) {
        this.locX = val - BattleViewingWindowManager.VIEWING_WINDOW_SIZE_X / 2;
    }

    public void setViewingWindowCenterY(final int val) {
        this.locY = val - BattleViewingWindowManager.VIEWING_WINDOW_SIZE_Y / 2;
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
        return BattleViewingWindowManager.VIEWING_WINDOW_SIZE_X;
    }

    public int getViewingWindowSizeY() {
        return BattleViewingWindowManager.VIEWING_WINDOW_SIZE_Y;
    }

    public int getOffsetFactorX() {
        return BattleViewingWindowManager.VIEWING_WINDOW_SIZE_X / 2;
    }

    public int getOffsetFactorY() {
        return BattleViewingWindowManager.VIEWING_WINDOW_SIZE_Y / 2;
    }
}
