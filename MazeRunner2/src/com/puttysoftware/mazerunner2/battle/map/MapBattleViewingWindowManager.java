/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.battle.map;

class MapBattleViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE = 19;

    // Constructors
    MapBattleViewingWindowManager() {
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
        return this.locX + MapBattleViewingWindowManager.VIEWING_WINDOW_SIZE
                - 1;
    }

    int getLowerRightViewingWindowLocationY() {
        return this.locY + MapBattleViewingWindowManager.VIEWING_WINDOW_SIZE
                - 1;
    }

    void setViewingWindowCenterX(int val) {
        this.locX = val
                - (MapBattleViewingWindowManager.VIEWING_WINDOW_SIZE / 2);
    }

    void setViewingWindowCenterY(int val) {
        this.locY = val
                - (MapBattleViewingWindowManager.VIEWING_WINDOW_SIZE / 2);
    }

    void offsetViewingWindowLocationX(int val) {
        this.locX += val;
    }

    void offsetViewingWindowLocationY(int val) {
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
        return MapBattleViewingWindowManager.VIEWING_WINDOW_SIZE;
    }
}
