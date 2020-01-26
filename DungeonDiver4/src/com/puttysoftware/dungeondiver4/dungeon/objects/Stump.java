/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Stump extends AbstractWall {
    // Constructors
    public Stump() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STUMP;
    }

    @Override
    public String getName() {
        return "Stump";
    }

    @Override
    public String getPluralName() {
        return "Stumps";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final DungeonObjectInventory inv) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Stumps stop movement, but not arrows, which pass over them unimpeded.";
    }
}