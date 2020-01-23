/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class GloveWall extends AbstractMultipleLock {
    // Constructors
    public GloveWall() {
        super(new Glove(), ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove Wall";
    }

    @Override
    public String getPluralName() {
        return "Glove Walls";
    }

    @Override
    public String getDescription() {
        return "Glove Walls are impassable without enough Gloves.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal Wall";
    }
}