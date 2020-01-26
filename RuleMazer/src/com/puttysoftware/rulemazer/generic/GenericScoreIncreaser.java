/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.objects.Empty;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public abstract class GenericScoreIncreaser extends MazeObject {
    // Constructors
    protected GenericScoreIncreaser() {
        super(false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Main.getApplication().getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        this.postMoveActionHook();
        Main.getApplication().getGameManager().redrawMaze();
    }

    public abstract void postMoveActionHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SCORE_INCREASER);
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
        Main.getApplication().getGameManager().morph(new Empty(), locX, locY,
                locZ);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SHATTER);
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
