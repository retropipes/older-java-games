/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractCheckKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class SunStone extends AbstractCheckKey {
    // Constructors
    public SunStone() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Sun Stone";
    }

    @Override
    public String getPluralName() {
        return "Sun Stones";
    }

    @Override
    public String getDescription() {
        return "Sun Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        inv.addItem(this);
        Application app = DungeonDiver4.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_SUN_STONE);
    }
}