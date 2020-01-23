/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractBarrier extends AbstractWall {
    // Constants
    private static final int BARRIER_DAMAGE_PERCENT = 2;

    // Constructors
    protected AbstractBarrier() {
        super(true, ColorConstants.COLOR_YELLOW);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Display impassable barrier message
        DungeonDiver4.getApplication()
                .showMessage("The barrier is impassable!");
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        // Hurt the player for trying to cross the barrier
        PartyManager.getParty().getLeader()
                .doDamagePercentage(AbstractBarrier.BARRIER_DAMAGE_PERCENT);
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public abstract int getBaseID();

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BARRIER);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}