/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericInventoryModifier;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class NoBoots extends GenericInventoryModifier {
    // Constructors
    public NoBoots() {
        super();
    }

    @Override
    public String getName() {
        return "No Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of No Boots";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        MazeMode.getApplication().getGameManager().decay();
        inv.removeAllBoots();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
    }

    @Override
    public String getDescription() {
        return "No Boots remove any boots worn when picked up.";
    }
}
