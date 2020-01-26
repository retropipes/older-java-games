/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.GenericPassThroughObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class FakeFinish extends GenericPassThroughObject {
    // Constructors
    public FakeFinish() {
        super();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
        MasterMaze.getApplication().showMessage("Fake exit!");
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