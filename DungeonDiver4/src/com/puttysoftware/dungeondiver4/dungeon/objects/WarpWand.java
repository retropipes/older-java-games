/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class WarpWand extends AbstractWand {
    public WarpWand() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Warp Wand";
    }

    @Override
    public String getPluralName() {
        return "Warp Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void useAction(final AbstractDungeonObject mo, final int x,
            final int y, final int z) {
        final Application app = DungeonDiver4.getApplication();
        app.getDungeonManager().getDungeon().warpObject(
                app.getDungeonManager().getDungeon().getCell(x, y, z,
                        DungeonConstants.LAYER_OBJECT),
                x, y, z, DungeonConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Warp Wands will teleport the object at the target square to a random location when used.";
    }
}
