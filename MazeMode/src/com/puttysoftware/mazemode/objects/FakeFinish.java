/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericPassThroughObject;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class FakeFinish extends GenericPassThroughObject {
    // Constructors
    public FakeFinish() {
        super();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
        MazeMode.getApplication().showMessage("Fake exit!");
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