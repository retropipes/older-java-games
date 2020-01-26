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

public class TabletSlot extends AbstractWall {
    // Constructors
    public TabletSlot() {
        super();
    }

    @Override
    public String getName() {
        return "Tablet Slot";
    }

    @Override
    public String getPluralName() {
        return "Tablet Slots";
    }

    @Override
    public String getDescription() {
        return "Tablet Slots can be opened with Tablets.";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TABLET_SLOT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Maze m = DDRemix.getApplication().getMazeManager().getMaze();
        if (m.getTablets() > 0) {
            m.useTablet();
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
        if (m.getTablets() == 0) {
            super.postMoveAction(ie, dirX, dirY);
        }
    }
}