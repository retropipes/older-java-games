/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractGround;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Tile extends AbstractGround {
    // Constructors
    public Tile() {
        super(true, true, true, true, ColorConstants.COLOR_NONE);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TILE;
    }

    @Override
    public String getName() {
        return "Tile";
    }

    @Override
    public String getPluralName() {
        return "Tiles";
    }

    @Override
    public String getDescription() {
        return "Tile is one of the many types of ground - unlike other types of ground, objects can be pushed and pulled over Tiles.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}