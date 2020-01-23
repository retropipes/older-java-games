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

public class DimnessGem extends AbstractGem {
    // Constructors
    public DimnessGem() {
        super(ColorConstants.COLOR_LIGHT_PURPLE);
    }

    @Override
    public String getName() {
        return "Dimness Gem";
    }

    @Override
    public String getPluralName() {
        return "Dimness Gems";
    }

    @Override
    public void postMoveActionHook() {
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .decrementVisionRadius();
        SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Dimness Gems decrease the visible area by 1.";
    }
}
