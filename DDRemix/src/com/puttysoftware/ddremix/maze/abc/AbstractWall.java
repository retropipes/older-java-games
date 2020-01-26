/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.abc;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.MazeConstants;
import com.puttysoftware.ddremix.maze.utilities.TypeConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public abstract class AbstractWall extends AbstractMazeObject {
    // Constructors
    protected AbstractWall() {
        super(true, true);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY) {
        DDRemix.getApplication().showMessage("Can't go that way");
        // Play move failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
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