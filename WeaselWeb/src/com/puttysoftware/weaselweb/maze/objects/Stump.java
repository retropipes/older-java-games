/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericWall;

public class Stump extends GenericWall {
    // Constructors
    public Stump() {
        super();
    }

    @Override
    public String getName() {
        return "Stump";
    }

    @Override
    public String getPluralName() {
        return "Stumps";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Stumps stop movement, but not arrows.";
    }
}