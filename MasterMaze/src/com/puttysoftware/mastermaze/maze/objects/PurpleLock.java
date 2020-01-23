/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleLock;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class PurpleLock extends GenericSingleLock {
    // Constructors
    public PurpleLock() {
        super(new PurpleKey(), ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            MasterMaze.getApplication().showMessage("You need a purple key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "Purple Lock";
    }

    @Override
    public String getPluralName() {
        return "Purple Locks";
    }

    @Override
    public String getDescription() {
        return "Purple Locks require Purple Keys to open.";
    }
}