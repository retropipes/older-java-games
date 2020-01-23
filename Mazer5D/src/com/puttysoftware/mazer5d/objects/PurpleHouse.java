/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Application;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class PurpleHouse extends FinishTo {
    // Constructors
    public PurpleHouse() {
        super();
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = Mazer5D.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UP);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Purple House";
    }

    @Override
    public String getPluralName() {
        return "Purple Houses";
    }

    @Override
    public String getDescription() {
        return "Purple Houses send you inside when walked on.";
    }
}