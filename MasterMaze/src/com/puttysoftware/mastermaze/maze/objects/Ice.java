/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericGround;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class Ice extends GenericGround {
    public Ice() {
        super(true, true, false, false, false, ColorConstants.COLOR_ICE);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CARPET;
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
        SoundManager.playSound(SoundConstants.SOUND_WALK_ICE);
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
