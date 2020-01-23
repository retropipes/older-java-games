/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractBarrier extends AbstractWall {
    // Constants
    private static final int BARRIER_DAMAGE_PERCENT = 2;

    // Constructors
    protected AbstractBarrier() {
        super(true, ColorConstants.COLOR_YELLOW);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        // Display impassable barrier message
        FantastleX.getApplication().showMessage("The barrier is impassable!");
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        // Hurt the player for trying to cross the barrier
        PartyManager.getParty().getLeader()
                .doDamagePercentage(AbstractBarrier.BARRIER_DAMAGE_PERCENT);
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