/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericRandomInvisibleTeleport;

public class RandomInvisibleTeleport extends GenericRandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleTeleport() {
        super(0, 0);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    public RandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    // Scriptability
    @Override
    public String getName() {
        return "Random Invisible Teleport";
    }

    @Override
    public String getPluralName() {
        return "Random Invisible Teleports";
    }

    @Override
    public String getDescription() {
        return "Random Invisible Teleports are both random and invisible.";
    }
}