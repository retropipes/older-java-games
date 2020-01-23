/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class IceBomb extends AbstractBomb {
    // Constructors
    public IceBomb() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Ice Bomb";
    }

    @Override
    public String getPluralName() {
        return "Ice Bombs";
    }

    @Override
    public String getDescription() {
        return "Ice Bombs freeze anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(int x, int y, int z) {
        // Freeze objects that react to ice
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanFreezeObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Freeze ground, too
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanFreezeGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
