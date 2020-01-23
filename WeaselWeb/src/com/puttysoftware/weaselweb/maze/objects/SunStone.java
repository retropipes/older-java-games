/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericInventoryableObject;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class SunStone extends GenericInventoryableObject {
    // Constants
    private static final long SCORE_GRAB_STONE = 1L;

    // Constructors
    public SunStone() {
        super(false, 0);
    }

    @Override
    public String getName() {
        return "Sun Stone";
    }

    @Override
    public String getPluralName() {
        return "Sun Stones";
    }

    @Override
    public String getDescription() {
        return "Sun Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        inv.addItem(this);
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SUN_STONE);
        WeaselWeb.getApplication().getGameManager()
                .addToScore(SunStone.SCORE_GRAB_STONE);
    }
}