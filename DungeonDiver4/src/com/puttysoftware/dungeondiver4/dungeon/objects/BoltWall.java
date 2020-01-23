/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class BoltWall extends AbstractMultipleLock {
    // Constructors
    public BoltWall() {
        super(new Bolt(), ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt Wall";
    }

    @Override
    public String getPluralName() {
        return "Bolt Walls";
    }

    @Override
    public String getDescription() {
        return "Bolt Walls are impassable without enough Bolts.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal Wall";
    }
}