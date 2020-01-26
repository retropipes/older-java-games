/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.objects.Empty;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericGem extends MazeObject {
    // Fields
    private static final long SCORE_SMASH = 10L;
    private static final long SCORE_GRAB = 20L;

    // Constructors
    protected GenericGem(final int tc) {
        super(false, true);
        this.setTemplateColor(tc);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        MasterMaze.getApplication().getGameManager().decay();
        MasterMaze.getApplication().getGameManager()
                .addToScore(GenericGem.SCORE_GRAB);
        this.postMoveActionHook();
        MasterMaze.getApplication().getGameManager().redrawMaze();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GEM;
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
        MasterMaze.getApplication().getGameManager().morph(new Empty(), locX,
                locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        MasterMaze.getApplication().getGameManager()
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
