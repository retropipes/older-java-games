/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPassThroughObject;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class FakeFinish extends AbstractPassThroughObject {
    // Constructors
    public FakeFinish() {
        super();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
        MazeRunnerII.getApplication().showMessage("Fake exit!");
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FAKE_FINISH;
    }

    @Override
    public String getName() {
        return "Fake Finish";
    }

    @Override
    public String getGameName() {
        return "Finish";
    }

    @Override
    public String getPluralName() {
        return "Fake Finishes";
    }

    @Override
    public String getDescription() {
        return "Fake Finishes look like regular finishes but don't lead anywhere.";
    }
}