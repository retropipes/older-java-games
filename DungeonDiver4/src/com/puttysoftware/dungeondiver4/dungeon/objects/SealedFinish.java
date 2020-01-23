/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class SealedFinish extends AbstractWall {
    // Constructors
    public SealedFinish() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SEALED_FINISH;
    }

    @Override
    public String getName() {
        return "Sealed Finish";
    }

    @Override
    public String getPluralName() {
        return "Sealed Finishes";
    }

    @Override
    public String getDescription() {
        return "Sealed Finishes are Finishes that are currently closed.";
    }
}