/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class TopazWall extends AbstractMultipleLock {
    // Constructors
    public TopazWall() {
        super(new TopazSquare(), ColorConstants.COLOR_SKY);
    }

    @Override
    public String getName() {
        return "Topaz Wall";
    }

    @Override
    public String getPluralName() {
        return "Topaz Walls";
    }

    @Override
    public String getDescription() {
        return "Topaz Walls are impassable without enough Topaz Squares.";
    }
}