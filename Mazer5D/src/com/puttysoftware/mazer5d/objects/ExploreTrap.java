/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericTrap;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class ExploreTrap extends GenericTrap {
    // Constructors
    public ExploreTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Explore Trap";
    }

    @Override
    public String getPluralName() {
        return "Explore Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        Mazer5D.getApplication().getMazeManager().getMaze()
                .addVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        Mazer5D.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Explore Traps turn exploring mode on, then disappear.";
    }
}