/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericTrap;
import com.puttysoftware.rulemazer.maze.effects.MazeEffectConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class CounterclockwiseRotationTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public CounterclockwiseRotationTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Counterclockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Counterclockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        Main.getApplication().showMessage("Your controls are rotated!");
        Main.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE,
                CounterclockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Counterclockwise Rotation Traps rotate your controls counterclockwise for 10 steps when stepped on.";
    }
}