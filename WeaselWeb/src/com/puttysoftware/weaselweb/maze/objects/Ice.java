/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericGround;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class Ice extends GenericGround {
    public Ice() {
        super(true, true, false, false, false);
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public String getPluralName() {
        return "Squares of Ice";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_ON_ICE);
    }

    @Override
    public String getDescription() {
        return "Ice is one of the many types of ground - it is frictionless. Anything that crosses it will slide.";
    }

    @Override
    public boolean hasFrictionConditionally(final ObjectInventory inv,
            final boolean moving) {
        if (inv.isItemThere(new GlueBoots())) {
            if (moving) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
