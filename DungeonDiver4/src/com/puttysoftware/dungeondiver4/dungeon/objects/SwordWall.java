/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class SwordWall extends AbstractMultipleLock {
    // Constructors
    public SwordWall() {
        super(new Sword(), ColorConstants.COLOR_SKY);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Sword Wall";
    }

    @Override
    public String getPluralName() {
        return "Sword Walls";
    }

    @Override
    public String getDescription() {
        return "Sword Walls are impassable without enough Swords.";
    }

    @Override
    public String getIdentifierV1() {
        return "Sky Crystal Wall";
    }
}