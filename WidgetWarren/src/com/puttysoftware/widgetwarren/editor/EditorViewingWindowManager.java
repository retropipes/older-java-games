/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor;

import com.puttysoftware.widgetwarren.maze.Maze;

public class EditorViewingWindowManager {
    // Fields
    private int locX, locY;
    private static final int VIEWING_WINDOW_SIZE_X = 13;
    private static final int VIEWING_WINDOW_SIZE_Y = 13;
    private static final int MIN_VIEWING_WINDOW_X = -(EditorViewingWindowManager.VIEWING_WINDOW_SIZE_X
            / 2);
    private static final int MIN_VIEWING_WINDOW_Y = -(EditorViewingWindowManager.VIEWING_WINDOW_SIZE_Y
            / 2);
    private int MAX_VIEWING_WINDOW_X;
    private int MAX_VIEWING_WINDOW_Y;

    // Constructors
    public EditorViewingWindowManager() {
        this.locX = EditorViewingWindowManager.MIN_VIEWING_WINDOW_X;
        this.locY = EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y;
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
        return this.locX + EditorViewingWindowManager.VIEWING_WINDOW_SIZE_X - 1;
    }

    public int getLowerRightViewingWindowLocationY() {
        return this.locY + EditorViewingWindowManager.VIEWING_WINDOW_SIZE_Y - 1;
    }

    public void setViewingWindowLocationX(final int val) {
        this.locX = val;
        this.checkViewingWindow();
    }

    public void setViewingWindowLocationY(final int val) {
        this.locY = val;
        this.checkViewingWindow();
    }

    public void setViewingWindowCenterX(final int val) {
        this.locX = val - this.getOffsetFactorX();
        this.checkViewingWindow();
    }

    public void setViewingWindowCenterY(final int val) {
        this.locY = val - this.getOffsetFactorY();
        this.checkViewingWindow();
    }

    public void offsetViewingWindowLocationX(final int val) {
        this.locX += val;
        this.checkViewingWindow();
    }

    public void offsetViewingWindowLocationY(final int val) {
        this.locY += val;
        this.checkViewingWindow();
    }

    public int getViewingWindowSizeX() {
        return EditorViewingWindowManager.VIEWING_WINDOW_SIZE_X;
    }

    public int getViewingWindowSizeY() {
        return EditorViewingWindowManager.VIEWING_WINDOW_SIZE_Y;
    }

    public int getMinimumViewingWindowLocationX() {
        return EditorViewingWindowManager.MIN_VIEWING_WINDOW_X;
    }

    public int getMinimumViewingWindowLocationY() {
        return EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y;
    }

    public int getMaximumViewingWindowLocationX() {
        return this.MAX_VIEWING_WINDOW_X;
    }

    public int getMaximumViewingWindowLocationY() {
        return this.MAX_VIEWING_WINDOW_Y;
    }

    public void halfOffsetMaximumViewingWindowLocationsFromMaze(final Maze m) {
        this.MAX_VIEWING_WINDOW_X = m.getColumns() + this.getOffsetFactorX();
        this.MAX_VIEWING_WINDOW_Y = m.getRows() + this.getOffsetFactorY();
    }

    public int getOffsetFactorX() {
        return (EditorViewingWindowManager.VIEWING_WINDOW_SIZE_X - 1) / 2;
    }

    public int getOffsetFactorY() {
        return (EditorViewingWindowManager.VIEWING_WINDOW_SIZE_Y - 1) / 2;
    }

    private void checkViewingWindow() {
        if (!this.isViewingWindowInBounds()) {
            this.fixViewingWindow();
        }
    }

    private boolean isViewingWindowInBounds() {
        if (this.locX >= EditorViewingWindowManager.MIN_VIEWING_WINDOW_X
                && this.locX <= this.MAX_VIEWING_WINDOW_X
                && this.locY >= EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y
                && this.locY <= this.MAX_VIEWING_WINDOW_Y) {
            return true;
        } else {
            return false;
        }
    }

    private void fixViewingWindow() {
        if (this.locX < EditorViewingWindowManager.MIN_VIEWING_WINDOW_X) {
            this.locX = EditorViewingWindowManager.MIN_VIEWING_WINDOW_X;
        }
        if (this.locX > this.MAX_VIEWING_WINDOW_X) {
            this.locX = this.MAX_VIEWING_WINDOW_X;
        }
        if (this.locY < EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y) {
            this.locY = EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y;
        }
        if (this.locY > this.MAX_VIEWING_WINDOW_Y) {
            this.locY = this.MAX_VIEWING_WINDOW_Y;
        }
    }
}
