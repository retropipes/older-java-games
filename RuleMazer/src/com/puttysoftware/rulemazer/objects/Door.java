/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericPassThroughObject;

public class Door extends GenericPassThroughObject {
    // Constructors
    public Door() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public String getPluralName() {
        return "Doors";
    }

    @Override
    public String getDescription() {
        return "Doors are purely decorative, but they do stop arrows from passing through.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        return false;
    }
}