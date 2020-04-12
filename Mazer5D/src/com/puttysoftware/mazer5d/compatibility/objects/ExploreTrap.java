/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTrap;
import com.puttysoftware.mazer5d.compatibility.maze.MazeConstants;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundPlayer.playSound(SoundIndex.CHANGE, SoundGroup.GAME);
        Mazer5D.getApplication().getMazeManager().getMaze()
                .addVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        Mazer5D.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Explore Traps turn exploring mode on, then disappear.";
    }
}