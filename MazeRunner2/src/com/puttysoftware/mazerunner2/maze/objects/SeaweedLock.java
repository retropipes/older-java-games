/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class SeaweedLock extends AbstractSingleLock {
    // Constructors
    public SeaweedLock() {
        super(new SeaweedKey(), ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            MazeRunnerII.getApplication().showMessage("You need a seaweed key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "Seaweed Lock";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Locks";
    }

    @Override
    public String getDescription() {
        return "Seaweed Locks require Seaweed Keys to open.";
    }
}