/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assetmanagers.SoundConstants;
import com.puttysoftware.mazer5d.assetmanagers.SoundManager;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericTrap;
import com.puttysoftware.mazer5d.maze.MazeConstants;

public class NoExploreTrap extends GenericTrap {
    // Constructors
    public NoExploreTrap() {
        super();
    }

    @Override
    public String getName() {
        return "No Explore Trap";
    }

    @Override
    public String getPluralName() {
        return "No Explore Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        Mazer5D.getApplication().getMazeManager().getMaze()
                .removeVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        Mazer5D.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "No Explore Traps turn exploring mode off, then disappear.";
    }
}