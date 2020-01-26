/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractWall;
import com.puttysoftware.ddremix.maze.utilities.TypeConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public class Lock extends AbstractWall {
    // Constructors
    public Lock() {
        super();
    }

    @Override
    public String getName() {
        return "Lock";
    }

    @Override
    public String getPluralName() {
        return "Locks";
    }

    @Override
    public String getDescription() {
        return "Locks can be opened with Keys.";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_LOCK;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Maze m = DDRemix.getApplication().getMazeManager().getMaze();
        if (m.getKeys() > 0) {
            m.useKey();
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
            m.setCell(new Empty(), dirX, dirY, 0, this.getLayer());
            return true;
        }
        return false;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Maze m = DDRemix.getApplication().getMazeManager().getMaze();
        if (m.getKeys() == 0) {
            super.postMoveAction(ie, dirX, dirY);
        }
    }
}