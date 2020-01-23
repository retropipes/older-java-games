/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class RubyWall extends AbstractMultipleLock {
    // Constructors
    public RubyWall() {
        super(new RubySquare(), ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Ruby Wall";
    }

    @Override
    public String getPluralName() {
        return "Ruby Walls";
    }

    @Override
    public String getDescription() {
        return "Ruby Walls are impassable without enough Ruby Squares.";
    }
}