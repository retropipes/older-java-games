/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.editor;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class EditorViewingWindowManager {
    // Fields
    private int oldLocX, oldLocY, locX, locY;
    private static final int VIEWING_WINDOW_SIZE_X = 13;
    private static final int VIEWING_WINDOW_SIZE_Y = 13;
    private static final int MIN_VIEWING_WINDOW_X = -(EditorViewingWindowManager.VIEWING_WINDOW_SIZE_X / 2);
    private static final int MIN_VIEWING_WINDOW_Y = -(EditorViewingWindowManager.VIEWING_WINDOW_SIZE_Y / 2);
    private int MAX_VIEWING_WINDOW_X;
    private int MAX_VIEWING_WINDOW_Y;

    // Constructors
    public EditorViewingWindowManager() {
        this.locX = EditorViewingWindowManager.MIN_VIEWING_WINDOW_X;
        this.locY = EditorViewingWindowManager.MIN_VIEWING_WINDOW_Y;
        this.oldLocX = this.locX;
        this.oldLocY = this.locY;
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

    public int getUpperLeftViewingWindowLocationX() {
        return this.locX;
    }

    public int getUpperLeftViewingWindowLocationY() {
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

    public void offsetViewingWindowLocationX(final int val) {
        this.locX += val;
        this.checkViewingWindow();
    }

    public void offsetViewingWindowLocationY(final int val) {
        this.locY += val;
        this.checkViewingWindow();
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

    public void setMaximumViewingWindowLocationX(final int value) {
        this.MAX_VIEWING_WINDOW_X = value;
        this.checkViewingWindow();
    }

    public void setMaximumViewingWindowLocationY(final int value) {
        this.MAX_VIEWING_WINDOW_Y = value;
        this.checkViewingWindow();
    }

    public void halfOffsetMaximumViewingWindowLocationsFromMaze(final Maze m) {
        for (int x = 0; x < m.getLevels(); x++) {
            this.MAX_VIEWING_WINDOW_X = m.getRows(x) + this.getOffsetFactorX();
            this.MAX_VIEWING_WINDOW_Y = m.getColumns(x) + this.getOffsetFactorY();
        }
    }

    public void halfOffsetMaximumViewingWindowLocation(final int valueX, final int valueY) {
        this.MAX_VIEWING_WINDOW_X = valueX + this.getOffsetFactorX();
        this.MAX_VIEWING_WINDOW_Y = valueY + this.getOffsetFactorY();
        this.checkViewingWindow();
    }

    public void halfOffsetMaximumViewingWindowLocationX(final int value) {
        this.MAX_VIEWING_WINDOW_X = value + this.getOffsetFactorX();
        this.checkViewingWindow();
    }

    public void halfOffsetMaximumViewingWindowLocationY(final int value) {
        this.MAX_VIEWING_WINDOW_Y = value + this.getOffsetFactorY();
        this.checkViewingWindow();
    }

    public void offsetMaximumViewingWindowLocationX(final int value) {
        this.MAX_VIEWING_WINDOW_X += value;
        this.checkViewingWindow();
    }

    public void offsetMaximumViewingWindowLocationY(final int value) {
        this.MAX_VIEWING_WINDOW_Y += value;
        this.checkViewingWindow();
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
        if (this.locX >= EditorViewingWindowManager.MIN_VIEWING_WINDOW_X && this.locX <= this.MAX_VIEWING_WINDOW_X
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
