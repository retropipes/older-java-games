/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractGem;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class DarknessGem extends AbstractGem {
    // Constructors
    public DarknessGem() {
        super(ColorConstants.COLOR_DARK_PURPLE);
    }

    @Override
    public String getName() {
        return "Darkness Gem";
    }

    @Override
    public String getPluralName() {
        return "Darkness Gems";
    }

    @Override
    public void postMoveActionHook() {
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .setVisionRadiusToMinimum();
        SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Darkness Gems decrease the visible area to its minimum.";
    }
}
