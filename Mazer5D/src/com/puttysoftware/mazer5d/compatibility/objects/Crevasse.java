/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWall;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.CREVASSE;
    }
}