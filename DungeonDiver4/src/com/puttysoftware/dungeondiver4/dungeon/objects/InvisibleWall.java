/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class InvisibleWall extends AbstractWall {
    // Constructors
    public InvisibleWall() {
        super(true, ColorConstants.COLOR_INVISIBLE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Display invisible wall message, if it's enabled
        DungeonDiver4.getApplication().showMessage("Invisible Wall!");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        // Disallow passing through Invisible Walls under ANY circumstances
        return true;
    }

    @Override
    public String getName() {
        return "Invisible Wall";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible Walls";
    }

    @Override
    public String getDescription() {
        return "Invisible Walls look like any other open space, but block any attempt at moving into them.";
    }
}