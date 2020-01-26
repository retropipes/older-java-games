/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class FireBomb extends AbstractBomb {
    // Constructors
    public FireBomb() {
        super(ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "Fire Bomb";
    }

    @Override
    public String getPluralName() {
        return "Fire Bombs";
    }

    @Override
    public String getDescription() {
        return "Fire Bombs burn anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Enrage objects that react to fire
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanEnrageObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Burn the ground, too
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanBurnGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
