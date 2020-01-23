/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPassThroughObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class FakeWall extends AbstractPassThroughObject {
    // Constructors
    public FakeWall() {
        super(true);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public int getGameTemplateColor() {
        return ColorConstants.COLOR_FAKE;
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
    }

    @Override
    public String getName() {
        return "Fake Wall";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Fake Walls";
    }

    @Override
    public String getDescription() {
        return "Fake Walls look like walls, but can be walked through.";
    }
}