/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.abc;

import com.puttysoftware.tallertower.maze.MazeConstants;
import com.puttysoftware.tallertower.maze.utilities.TypeConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

public abstract class AbstractPassThroughObject extends AbstractMazeObject {
    // Constructors
    protected AbstractPassThroughObject() {
        super(false, true, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}