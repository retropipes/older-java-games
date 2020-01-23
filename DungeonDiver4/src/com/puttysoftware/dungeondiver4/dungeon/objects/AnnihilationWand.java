/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class AnnihilationWand extends AbstractWand {
    // Constructors
    public AnnihilationWand() {
        super(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Annihilation Wand";
    }

    @Override
    public String getPluralName() {
        return "Annihilation Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getDescription() {
        return "Annihilation Wands will destroy any object (not ground) when used, except the Void or a Sealing Wall.";
    }
}
