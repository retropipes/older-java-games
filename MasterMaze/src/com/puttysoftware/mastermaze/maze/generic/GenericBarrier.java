/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericBarrier extends GenericWall {
    // Constants
    private static final int BARRIER_DAMAGE_PERCENT = 2;

    // Constructors
    protected GenericBarrier() {
        super(true, ColorConstants.COLOR_YELLOW);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Display impassable barrier message
        MasterMaze.getApplication().showMessage("The barrier is impassable!");
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        // Hurt the player for trying to cross the barrier
        PartyManager.getParty().getLeader()
                .doDamagePercentage(GenericBarrier.BARRIER_DAMAGE_PERCENT);
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