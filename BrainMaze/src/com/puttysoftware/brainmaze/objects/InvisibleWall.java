/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericWall;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class InvisibleWall extends GenericWall {
    // Constructors
    public InvisibleWall() {
        super(true, ColorConstants.COLOR_INVISIBLE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Display invisible wall message, if it's enabled
        BrainMaze.getApplication().showMessage("Invisible Wall!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        // Disallow passing through Invisible Walls under ANY circumstances
        return true;
    }

    @Override
    public String getName() {
        return "Invisible Wall";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible Walls";
    }

    @Override
    public String getDescription() {
        return "Invisible Walls look like any other open space, but block any attempt at moving into them.";
    }
}