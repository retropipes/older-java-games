/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

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
        MazeRunnerII.getApplication().showMessage("The barrier is impassable!");
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