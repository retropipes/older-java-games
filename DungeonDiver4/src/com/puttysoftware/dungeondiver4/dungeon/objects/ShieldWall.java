/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class ShieldWall extends AbstractMultipleLock {
    // Constructors
    public ShieldWall() {
        super(new Shield(), ColorConstants.COLOR_ORANGE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SHIELD;
    }

    @Override
    public String getName() {
        return "Shield Wall";
    }

    @Override
    public String getPluralName() {
        return "Shield Walls";
    }

    @Override
    public String getDescription() {
        return "Shield Walls are impassable without enough Shields.";
    }

    @Override
    public String getIdentifierV1() {
        return "Orange Crystal Wall";
    }
}