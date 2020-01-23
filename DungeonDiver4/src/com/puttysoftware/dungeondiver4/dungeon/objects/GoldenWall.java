/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class GoldenWall extends AbstractMultipleLock {
    // Constructors
    public GoldenWall() {
        super(new GoldenSquare(), ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Golden Wall";
    }

    @Override
    public String getPluralName() {
        return "Golden Walls";
    }

    @Override
    public String getDescription() {
        return "Golden Walls are impassable without enough Golden Squares.";
    }
}