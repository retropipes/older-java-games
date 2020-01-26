/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericWall;

public class Crevasse extends GenericWall {
    // Constructors
    public Crevasse() {
        super();
    }

    @Override
    public String getName() {
        return "Crevasse";
    }

    @Override
    public String getPluralName() {
        return "Crevasses";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Crevasses stop movement, but not arrows, which pass over them unimpeded.";
    }
}