/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericTrap;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .removeVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        WeaselWeb.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "No Explore Traps turn exploring mode off, then disappear.";
    }
}