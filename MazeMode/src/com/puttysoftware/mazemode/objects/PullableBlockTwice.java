/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericMovableObject;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class PullableBlockTwice extends GenericMovableObject {
    // Constructors
    public PullableBlockTwice() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block Twice";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Twice";
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = MazeMode.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PULL);
        app.getGameManager().morphOther(new PullableBlockOnce(), pushX, pushY,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Twice can only be pulled twice, before turning into a wall.";
    }
}