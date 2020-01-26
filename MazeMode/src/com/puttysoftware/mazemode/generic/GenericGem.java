/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.generic;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.objects.Empty;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public abstract class GenericGem extends MazeObject {
    // Fields
    private static final long SCORE_SMASH = 10L;
    private static final long SCORE_GRAB = 20L;

    // Constructors
    protected GenericGem() {
        super(false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        MazeMode.getApplication().getGameManager().decay();
        MazeMode.getApplication().getGameManager()
                .addToScore(GenericGem.SCORE_GRAB);
        this.postMoveActionHook();
        MazeMode.getApplication().getGameManager().redrawMaze();
    }

    public abstract void postMoveActionHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GEM);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        MazeMode.getApplication().getGameManager().morph(new Empty(), locX,
                locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SHATTER);
        MazeMode.getApplication().getGameManager()
                .addToScore(GenericGem.SCORE_SMASH);
        return false;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
