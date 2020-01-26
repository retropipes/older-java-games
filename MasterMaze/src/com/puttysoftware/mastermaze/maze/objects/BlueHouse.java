/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleportTo;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class BlueHouse extends GenericTeleportTo {
    // Constructors
    public BlueHouse() {
        super(ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_UP);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Blue House";
    }

    @Override
    public String getPluralName() {
        return "Blue Houses";
    }

    @Override
    public String getDescription() {
        return "Blue Houses send you inside when walked on.";
    }
}