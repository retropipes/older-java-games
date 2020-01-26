/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class ShockBomb extends AbstractBomb {
    // Constructors
    public ShockBomb() {
        super(ColorConstants.COLOR_LIGHT_YELLOW);
    }

    @Override
    public String getName() {
        return "Shock Bomb";
    }

    @Override
    public String getPluralName() {
        return "Shock Bombs";
    }

    @Override
    public String getDescription() {
        return "Shock Bombs shock anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Shock objects that react to shock
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanShockObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Shock the ground, too
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanShockGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
