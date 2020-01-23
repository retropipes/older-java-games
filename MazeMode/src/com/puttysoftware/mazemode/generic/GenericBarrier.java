/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.generic;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public abstract class GenericBarrier extends GenericWall {
    // Constants
    private static final int BARRIER_DAMAGE_PERCENT = 2;

    // Constructors
    protected GenericBarrier() {
        super();
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Display impassable barrier message
        final Application app = MazeMode.getApplication();
        MazeMode.getApplication().showMessage("The barrier is impassable!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
        // Hurt the player for trying to cross the barrier
        app.getMazeManager().getMaze()
                .doDamagePercentage(GenericBarrier.BARRIER_DAMAGE_PERCENT);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BARRIER);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}