/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericTrap;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class ArrowTrap extends GenericTrap {
    // Constructors
    public ArrowTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Arrow Trap";
    }

    @Override
    public String getPluralName() {
        return "Arrow Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        Mazer5D.getApplication().showMessage("The arrow is stopped!");
        return false;
    }

    @Override
    public String getDescription() {
        return "Arrow Traps stop arrows.";
    }
}