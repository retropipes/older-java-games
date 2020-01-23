/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.game;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.maze.Maze;

public class PlayerLocationManager {
    // Fields
    private int oldLocX, oldLocY, oldLocZ, oldLocW;
    private int locX, locY, locZ, locW;

    // Constructors
    public PlayerLocationManager() {
        this.resetPlayerLocation();
    }

    // Methods
    public int getPlayerLocationX() {
        return this.locX;
    }

    public int getPlayerLocationY() {
        return this.locY;
    }

    public int getPlayerLocationZ() {
        return this.locZ;
    }

    public int getPlayerLocationW() {
        return this.locW;
    }

    public void setPlayerLocationW(final int val) {
        this.locW = val;
    }

    public void setPlayerLocation(final int valX, final int valY,
            final int valZ, final int valW) {
        this.locX = valX;
        this.locY = valY;
        this.locZ = valZ;
        this.locW = valW;
        this.fixLocations();
    }

    public void offsetPlayerLocationX(final int val) {
        this.locX += val;
    }

    public void offsetPlayerLocationY(final int val) {
        this.locY += val;
    }

    public void resetPlayerLocation() {
        this.locX = 0;
        this.locY = 0;
        this.locZ = 0;
        this.locW = 0;
        this.oldLocX = 0;
        this.oldLocY = 0;
        this.oldLocZ = 0;
        this.oldLocW = 0;
    }

    public void savePlayerLocation() {
        this.oldLocX = this.locX;
        this.oldLocY = this.locY;
        this.oldLocZ = this.locZ;
        this.oldLocW = this.locW;
    }

    public void restorePlayerLocation() {
        this.locX = this.oldLocX;
        this.locY = this.oldLocY;
        this.locZ = this.oldLocZ;
        this.locW = this.oldLocW;
        this.fixLocations();
    }

    private void fixLocations() {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
        if (m.is3rdDimensionWraparoundEnabled()) {
            if (this.locZ < 0) {
                this.locZ = m.getFloors() - 1;
            } else if (this.locZ > m.getFloors() - 1) {
                this.locZ = 0;
            }
        }
    }
}
