/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class NecklaceWall extends AbstractMultipleLock {
    // Constructors
    public NecklaceWall() {
        super(new Necklace(), ColorConstants.COLOR_ROSE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NECKLACE;
    }

    @Override
    public String getName() {
        return "Necklace Wall";
    }

    @Override
    public String getPluralName() {
        return "Necklace Walls";
    }

    @Override
    public String getDescription() {
        return "Necklace Walls are impassable without enough Necklaces.";
    }

    @Override
    public String getIdentifierV1() {
        return "Rose Crystal Wall";
    }
}