/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class DisarmTrapWand extends AbstractWand {
    // Constructors
    public DisarmTrapWand() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Disarm Trap Wand";
    }

    @Override
    public String getPluralName() {
        return "Disarm Trap Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getDescription() {
        return "Disarm Trap Wands will make one trap disappear when used, if aimed at a trap.";
    }
}
