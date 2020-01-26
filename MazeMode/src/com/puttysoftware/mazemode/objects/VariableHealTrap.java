/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericTrap;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;
import com.puttysoftware.randomnumbers.RandomRange;

public class VariableHealTrap extends GenericTrap {
    // Fields
    private RandomRange healingGiven;
    private static final int MIN_HEALING = 1;
    private int maxHealing;

    // Constructors
    public VariableHealTrap() {
        super();
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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.maxHealing = MazeMode.getApplication().getMazeManager().getMaze()
                .getMaximumHP() / 10;
        if (this.maxHealing < VariableHealTrap.MIN_HEALING) {
            this.maxHealing = VariableHealTrap.MIN_HEALING;
        }
        this.healingGiven = new RandomRange(VariableHealTrap.MIN_HEALING,
                this.maxHealing);
        MazeMode.getApplication().getMazeManager().getMaze()
                .heal(this.healingGiven.generate());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
        MazeMode.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}