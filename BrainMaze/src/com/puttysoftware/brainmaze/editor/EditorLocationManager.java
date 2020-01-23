/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.editor;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.maze.Maze;
import com.puttysoftware.brainmaze.maze.MazeConstants;

public class EditorLocationManager {
    // Fields
    private int locX, locY, locZ;
    private int locW, locE;
    private int cameFromZ;
    private int minX, minY, minZ, minW, minE;
    private int maxW, maxE;
    private int maxX, maxY, maxZ;

    // Constructors
    public EditorLocationManager() {
        this.resetEditorLocation();
    }

    // Methods
    public int getEditorLocationX() {
        return this.locX;
    }

    public int getEditorLocationY() {
        return this.locY;
    }

    public int getEditorLocationZ() {
        return this.locZ;
    }

    public int getEditorLocationW() {
        return this.locW;
    }

    public int getEditorLocationE() {
        return this.locE;
    }

    public int getMaxEditorLocationZ() {
        return this.maxZ;
    }

    public int getMaxEditorLocationW() {
        return this.maxW;
    }

    public int getMinEditorLocationZ() {
        return this.minZ;
    }

    public int getMinEditorLocationW() {
        return this.minW;
    }

    public int getCameFromZ() {
        return this.cameFromZ;
    }

    public void setEditorLocationX(final int val) {
        this.locX = val;
        this.checkLimits();
    }

    public void setEditorLocationY(final int val) {
        this.locY = val;
        this.checkLimits();
    }

    public void setEditorLocationZ(final int val) {
        this.locZ = val;
        this.checkLimits();
    }

    public void setEditorLocationW(final int val) {
        this.locW = val;
        this.checkLimits();
    }

    public void setEditorLocationE(final int val) {
        this.locE = val;
        this.checkLimits();
    }

    public void setCameFromZ(final int val) {
        this.cameFromZ = val;
    }

    public void offsetEditorLocationZ(final int val) {
        this.locZ += val;
        this.checkLimits();
    }

    public void offsetEditorLocationW(final int val) {
        this.locW += val;
        this.checkLimits();
    }

    public void setLimitsFromMaze(final Maze m) {
        this.minX = 0;
        this.minY = 0;
        this.minZ = 0;
        this.minW = 0;
        this.minE = 0;
        this.maxW = m.getLevels() - 1;
        this.maxE = MazeConstants.LAYER_COUNT - 1;
        this.maxX = m.getRows();
        this.maxY = m.getColumns();
        this.maxZ = m.getFloors() - 1;
    }

    public final void resetEditorLocation() {
        this.locX = 0;
        this.locY = 0;
        this.locZ = 0;
        this.locW = 0;
        this.locE = 0;
        this.cameFromZ = 0;
        this.maxX = 0;
        this.maxY = 0;
        this.maxZ = 0;
        this.maxW = 0;
        this.maxE = 0;
        this.minX = 0;
        this.minY = 0;
        this.minZ = 0;
        this.minW = 0;
        this.minE = 0;
    }

    private void checkLimits() {
        // Check for limits out of bounds
        final Maze m = BrainMaze.getApplication().getMazeManager().getMaze();
        if (this.locW < this.minW) {
            this.locW = this.minW;
        }
        if (this.locW > this.maxW) {
            this.locW = this.maxW;
        }
        if (this.locX < this.minX) {
            this.locX = this.minX;
        }
        if (this.locX > this.maxX) {
            this.locX = this.maxX;
        }
        if (this.locY < this.minY) {
            this.locY = this.minY;
        }
        if (this.locY > this.maxY) {
            this.locY = this.maxY;
        }
        if (this.locZ < this.minZ) {
            if (m.is3rdDimensionWraparoundEnabled()) {
                this.locZ = this.maxZ;
            } else {
                this.locZ = this.minZ;
            }
        }
        if (this.locZ > this.maxZ) {
            if (m.is3rdDimensionWraparoundEnabled()) {
                this.locZ = this.minZ;
            } else {
                this.locZ = this.maxZ;
            }
        }
        if (this.locE < this.minE) {
            this.locE = this.minE;
        }
        if (this.locE > this.maxE) {
            this.locE = this.maxE;
        }
    }
}
