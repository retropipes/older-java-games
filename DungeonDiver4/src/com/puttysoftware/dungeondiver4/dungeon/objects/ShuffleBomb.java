/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class ShuffleBomb extends AbstractBomb {
    // Constructors
    public ShuffleBomb() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public String getName() {
        return "Shuffle Bomb";
    }

    @Override
    public String getPluralName() {
        return "Shuffle Bombs";
    }

    @Override
    public String getDescription() {
        return "Shuffle Bombs randomly rearrange anything in an area of radius 3 centered on the target point.";
    }

    @Override
    public void useActionHook(int x, int y, int z) {
        // Shuffle objects
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .radialScanShuffleObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}