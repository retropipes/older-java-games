/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericTrap;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class VariableHurtTrap extends GenericTrap {
    // Fields
    private RandomRange damageDealt;
    private static final int MIN_DAMAGE = 1;
    private int maxDamage;

    // Constructors
    public VariableHurtTrap() {
        super();
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        this.maxDamage = Mazer5D.getApplication().getMazeManager().getMaze()
                .getMaximumHP() / 10;
        if (this.maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            this.maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        this.damageDealt = new RandomRange(VariableHurtTrap.MIN_DAMAGE,
                this.maxDamage);
        Mazer5D.getApplication().getMazeManager().getMaze()
                .doDamage(this.damageDealt.generate());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
        Mazer5D.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}