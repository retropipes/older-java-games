/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class DaggerWall extends AbstractMultipleLock {
    // Constructors
    public DaggerWall() {
        super(new Dagger(), ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger Wall";
    }

    @Override
    public String getPluralName() {
        return "Dagger Walls";
    }

    @Override
    public String getDescription() {
        return "Dagger Walls are impassable without enough Daggers.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal Wall";
    }
}