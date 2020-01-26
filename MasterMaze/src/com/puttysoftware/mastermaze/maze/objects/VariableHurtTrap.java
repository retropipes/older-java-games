/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTrap;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class VariableHurtTrap extends GenericTrap {
    // Fields
    private RandomRange damageDealt;
    private static final int MIN_DAMAGE = 1;
    private int maxDamage;

    // Constructors
    public VariableHurtTrap() {
        super(ColorConstants.COLOR_SKY,
                ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HEALTH,
                ColorConstants.COLOR_DARK_SKY);
    }

    @Override
    public String getName() {
        return "Variable Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.maxDamage = PartyManager.getParty().getLeader().getMaximumHP()
                / 10;
        if (this.maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            this.maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        this.damageDealt = new RandomRange(VariableHurtTrap.MIN_DAMAGE,
                this.maxDamage);
        PartyManager.getParty().getLeader()
                .doDamage(this.damageDealt.generate());
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        MasterMaze.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}