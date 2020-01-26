/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class DarkWand extends AbstractWand {
    // Constructors
    public DarkWand() {
        super(ColorConstants.COLOR_BLACK);
    }

    @Override
    public String getName() {
        return "Dark Wand";
    }

    @Override
    public String getPluralName() {
        return "Dark Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        final Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon();
        final AbstractDungeonObject obj = m.getCell(x, y, z,
                DungeonConstants.LAYER_OBJECT);
        if (obj.getName().equals("Empty")) {
            // Create a Dark Gem
            this.useAction(new DarkGem(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
        } else if (obj.getName().equals("Light Gem")) {
            // Destroy the Light Gem
            this.useAction(new Empty(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        }
    }

    @Override
    public String getDescription() {
        return "Dark Wands have 2 uses. When aimed at an empty space, they create a Dark Gem. When aimed at a Light Gem, it is destroyed.";
    }
}
