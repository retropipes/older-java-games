/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.GenericRandomInvisibleTeleport;

public class RandomInvisibleTeleport extends GenericRandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleTeleport() {
        super(0, 0, "random");
    }

    public RandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX, "random");
    }

    // Scriptability
    @Override
    public String getName() {
        return "Random Invisible Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
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