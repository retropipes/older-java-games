/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class MoveWall extends AbstractMultipleLock {
    // Constructors
    public MoveWall() {
        super(new Move(), ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_MOVE;
    }

    @Override
    public String getName() {
        return "Move Wall";
    }

    @Override
    public String getPluralName() {
        return "Move Walls";
    }

    @Override
    public String getDescription() {
        return "Move Walls are impassable without enough Moves.";
    }

    @Override
    public String getIdentifierV1() {
        return "White Crystal Wall";
    }
}