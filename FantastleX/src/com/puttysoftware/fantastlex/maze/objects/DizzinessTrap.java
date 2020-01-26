/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractTrap;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class DizzinessTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DizzinessTrap() {
        super(ColorConstants.COLOR_LIGHT_GREEN,
                ObjectImageConstants.OBJECT_IMAGE_DIZZINESS,
                ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
        return "Dizziness Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        FantastleX.getApplication().showMessage("You feel dizzy!");
        FantastleX.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_DIZZY,
                DizzinessTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_DIZZY);
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 10 steps when stepped on.";
    }
}