/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class PoisonBomb extends AbstractBomb {
    // Constructors
    public PoisonBomb() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Poison Bomb";
    }

    @Override
    public String getPluralName() {
        return "Poison Bombs";
    }

    @Override
    public String getDescription() {
        return "Poison Bombs poison anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(int x, int y, int z) {
        // Poison objects that react to poison
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanPoisonObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Poison the ground, too
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanPoisonGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
