/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.editor;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;

public class EditorViewingWindowManager {
    // Fields
    private int locX, locY;
    private int MIN_VIEWING_WINDOW_X;
    private int MIN_VIEWING_WINDOW_Y;
    private int MAX_VIEWING_WINDOW_X;
    private int MAX_VIEWING_WINDOW_Y;

    // Constructors
    public EditorViewingWindowManager() {
        this.locX = 0;
        this.locY = 0;
        this.MIN_VIEWING_WINDOW_X = 0;
        this.MIN_VIEWING_WINDOW_Y = 0;
        this.MAX_VIEWING_WINDOW_X = 0;
        this.MAX_VIEWING_WINDOW_Y = 0;
    }

    // Methods
    public int getViewingWindowLocationX() {
        return this.locX;
    }

    public int getViewingWindowLocationY() {
        return this.locY;
    }

    public int getLowerRightViewingWindowLocationX() {
        return this.locX + PreferencesManager.getViewingWindowSize() - 1;
    }

    public int getLowerRightViewingWindowLocationY() {
        return this.locY + PreferencesManager.getViewingWindowSize() - 1;
    }

    public void setViewingWindowLocationX(int val) {
        this.locX = val;
        this.checkViewingWindow();
    }

    public void setViewingWindowLocationY(int val) {
        this.locY = val;
        this.checkViewingWindow();
    }

    public void setViewingWindowCenterX(int val) {
        this.locX = val - this.getOffsetFactorX();
        this.checkViewingWindow();
    }

    public void setViewingWindowCenterY(int val) {
        this.locY = val - this.getOffsetFactorY();
        this.checkViewingWindow();
    }

    public void offsetViewingWindowLocationX(int val) {
        this.locX += val;
        this.checkViewingWindow();
    }

    public void offsetViewingWindowLocationY(int val) {
        this.locY += val;
        this.checkViewingWindow();
    }

    public int getViewingWindowSizeX() {
        return PreferencesManager.getViewingWindowSize();
    }

    public int getViewingWindowSizeY() {
        return PreferencesManager.getViewingWindowSize();
    }

    public int getMinimumViewingWindowLocationX() {
        return this.MIN_VIEWING_WINDOW_X;
    }

    public int getMinimumViewingWindowLocationY() {
        return this.MIN_VIEWING_WINDOW_Y;
    }

    public int getMaximumViewingWindowLocationX() {
        return this.MAX_VIEWING_WINDOW_X;
    }

    public int getMaximumViewingWindowLocationY() {
        return this.MAX_VIEWING_WINDOW_Y;
    }

    public void halfOffsetMaximumViewingWindowLocationsFromDungeon(Dungeon m) {
        this.MIN_VIEWING_WINDOW_X = -(PreferencesManager.getViewingWindowSize() / 2);
        this.MIN_VIEWING_WINDOW_Y = -(PreferencesManager.getViewingWindowSize() / 2);
        this.MAX_VIEWING_WINDOW_X = m.getColumns() + this.getOffsetFactorX();
        this.MAX_VIEWING_WINDOW_Y = m.getRows() + this.getOffsetFactorY();
        this.locX = this.MIN_VIEWING_WINDOW_X;
        this.locY = this.MIN_VIEWING_WINDOW_Y;
    }

    public int getOffsetFactorX() {
        return (PreferencesManager.getViewingWindowSize() - 1) / 2;
    }

    public int getOffsetFactorY() {
        return (PreferencesManager.getViewingWindowSize() - 1) / 2;
    }

    private void checkViewingWindow() {
        if (!this.isViewingWindowInBounds()) {
            this.fixViewingWindow();
        }
    }

    private boolean isViewingWindowInBounds() {
        if (this.locX >= this.MIN_VIEWING_WINDOW_X
                && this.locX <= this.MAX_VIEWING_WINDOW_X
                && this.locY >= this.MIN_VIEWING_WINDOW_Y
                && this.locY <= this.MAX_VIEWING_WINDOW_Y) {
            return true;
        } else {
            return false;
        }
    }

    private void fixViewingWindow() {
        if (this.locX < this.MIN_VIEWING_WINDOW_X) {
            this.locX = this.MIN_VIEWING_WINDOW_X;
        }
        if (this.locX > this.MAX_VIEWING_WINDOW_X) {
            this.locX = this.MAX_VIEWING_WINDOW_X;
        }
        if (this.locY < this.MIN_VIEWING_WINDOW_Y) {
            this.locY = this.MIN_VIEWING_WINDOW_Y;
        }
        if (this.locY > this.MAX_VIEWING_WINDOW_Y) {
            this.locY = this.MAX_VIEWING_WINDOW_Y;
        }
    }
}
