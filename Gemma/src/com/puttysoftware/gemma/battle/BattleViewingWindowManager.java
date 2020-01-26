/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

class BattleViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE = 19;

    // Constructors
    BattleViewingWindowManager() {
        this.locX = 0;
        this.locY = 0;
        this.oldLocX = 0;
        this.oldLocY = 0;
    }

    // Methods
    int getViewingWindowLocationX() {
        return this.locX;
    }

    int getViewingWindowLocationY() {
        return this.locY;
    }

    int getLowerRightViewingWindowLocationX() {
        return this.locX + BattleViewingWindowManager.VIEWING_WINDOW_SIZE - 1;
    }

    int getLowerRightViewingWindowLocationY() {
        return this.locY + BattleViewingWindowManager.VIEWING_WINDOW_SIZE - 1;
    }

    void setViewingWindowCenterX(final int val) {
        this.locX = val - BattleViewingWindowManager.VIEWING_WINDOW_SIZE / 2;
    }

    void setViewingWindowCenterY(final int val) {
        this.locY = val - BattleViewingWindowManager.VIEWING_WINDOW_SIZE / 2;
    }

    void offsetViewingWindowLocationX(final int val) {
        this.locX += val;
    }

    void offsetViewingWindowLocationY(final int val) {
        this.locY += val;
    }

    void saveViewingWindow() {
        this.oldLocX = this.locX;
        this.oldLocY = this.locY;
    }

    void restoreViewingWindow() {
        this.locX = this.oldLocX;
        this.locY = this.oldLocY;
    }

    static int getViewingWindowSize() {
        return BattleViewingWindowManager.VIEWING_WINDOW_SIZE;
    }
}
