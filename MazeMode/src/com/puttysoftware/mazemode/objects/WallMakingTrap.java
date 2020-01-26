/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericTrap;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class WallMakingTrap extends GenericTrap {
    public WallMakingTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Wall-Making Trap";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
        MazeMode.getApplication().getGameManager().delayedDecayTo(new Wall());
    }

    @Override
    public String getDescription() {
        return "Wall-Making Traps create a Wall when you step OFF them.";
    }
}
