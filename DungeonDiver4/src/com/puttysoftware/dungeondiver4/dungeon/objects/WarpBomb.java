/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBomb;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class WarpBomb extends AbstractBomb {
    // Constructors
    public WarpBomb() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getName() {
        return "Warp Bomb";
    }

    @Override
    public String getPluralName() {
        return "Warp Bombs";
    }

    @Override
    public String getDescription() {
        return "Warp Bombs randomly teleport anything around the center of the target point.";
    }

    @Override
    public void useActionHook(int x, int y, int z) {
        SoundManager.playSound(SoundConstants.SOUND_EXPLODE);
        DungeonDiver4
                .getApplication()
                .getDungeonManager()
                .getDungeon()
                .radialScanWarpObjects(x, y, z, DungeonConstants.LAYER_OBJECT,
                        AbstractBomb.EFFECT_RADIUS);
    }
}