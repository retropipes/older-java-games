/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTrap;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class HurtTrap extends AbstractTrap {
    // Fields
    private int damage;

    // Constructors
    public HurtTrap() {
        super(ColorConstants.COLOR_SKY,
                ObjectImageConstants.OBJECT_IMAGE_HEALTH,
                ColorConstants.COLOR_DARK_SKY);
    }

    @Override
    public String getName() {
        return "Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Hurt Traps";
    }

    @Override
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        this.damage = PartyManager.getParty().getLeader().getMaximumHP() / 50;
        if (this.damage < 1) {
            this.damage = 1;
        }
        PartyManager.getParty().getLeader().doDamage(this.damage);
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }
}