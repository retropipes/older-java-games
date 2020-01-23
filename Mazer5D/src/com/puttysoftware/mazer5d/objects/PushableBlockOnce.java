/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Application;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericMovableObject;
import com.puttysoftware.mazer5d.generic.MazeObject;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class PushableBlockOnce extends GenericMovableObject {
    // Constructors
    public PushableBlockOnce() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block Once";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks Once";
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = Mazer5D.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
        app.getGameManager().morphOther(new Wall(), pushX, pushY,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks Once can only be pushed once, before turning into a wall.";
    }
}