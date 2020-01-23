/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericBlockTeleport extends GenericTeleport {
    // Constructors
    protected GenericBlockTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int attrName) {
        super(destinationRow, destinationColumn, destinationFloor, true,
                attrName);
        this.setTemplateColor(ColorConstants.COLOR_ORANGE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_PURPLE);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        final Application app = MasterMaze.getApplication();
        final GenericMovableObject pushedInto = (GenericMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void pullIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        final Application app = MasterMaze.getApplication();
        final GenericMovableObject pushedInto = (GenericMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }
}
