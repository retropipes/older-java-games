/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game;

class GameViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE = 19;

    // Constructors
    GameViewingWindowManager() {
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
        return this.locX + GameViewingWindowManager.VIEWING_WINDOW_SIZE - 1;
    }

    int getLowerRightViewingWindowLocationY() {
        return this.locY + GameViewingWindowManager.VIEWING_WINDOW_SIZE - 1;
    }

    void setViewingWindowLocationX(final int val) {
        this.locX = val;
    }

    void setViewingWindowLocationY(final int val) {
        this.locY = val;
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
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE;
    }

    static int getOffsetFactor() {
        return GameViewingWindowManager.VIEWING_WINDOW_SIZE / 2;
    }
}
