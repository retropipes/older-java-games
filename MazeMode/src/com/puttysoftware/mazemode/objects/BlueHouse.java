/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class BlueHouse extends FinishTo {
    // Constructors
    public BlueHouse() {
        super();
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MazeMode.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UP);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Blue House";
    }

    @Override
    public String getPluralName() {
        return "Blue Houses";
    }

    @Override
    public String getDescription() {
        return "Blue Houses send you inside when walked on.";
    }
}