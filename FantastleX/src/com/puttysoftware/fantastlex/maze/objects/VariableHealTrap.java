/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.maze.abc.AbstractTrap;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class VariableHealTrap extends AbstractTrap {
    // Fields
    private RandomRange healingGiven;
    private static final int MIN_HEALING = 1;
    private int maxHealing;

    // Constructors
    public VariableHealTrap() {
        super(ColorConstants.COLOR_MAGENTA,
                ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HEALTH,
                ColorConstants.COLOR_DARK_MAGENTA);
    }

    @Override
    public String getName() {
        return "Variable Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        this.maxHealing = PartyManager.getParty().getLeader().getMaximumHP() / 10;
        if (this.maxHealing < VariableHealTrap.MIN_HEALING) {
            this.maxHealing = VariableHealTrap.MIN_HEALING;
        }
        this.healingGiven = new RandomRange(VariableHealTrap.MIN_HEALING,
                this.maxHealing);
        PartyManager.getParty().getLeader().heal(this.healingGiven.generate());
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        FantastleX.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}