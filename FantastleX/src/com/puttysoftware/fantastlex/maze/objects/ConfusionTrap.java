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

public class ConfusionTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ConfusionTrap() {
        super(ColorConstants.COLOR_LIGHT_RED,
                ObjectImageConstants.OBJECT_IMAGE_CONFUSION,
                ColorConstants.COLOR_DARK_RED);
    }

    @Override
    public String getName() {
        return "Confusion Trap";
    }

    @Override
    public String getPluralName() {
        return "Confusion Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        FantastleX.getApplication().showMessage("You are confused!");
        FantastleX.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_CONFUSED,
                ConfusionTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_CONFUSED);
    }

    @Override
    public String getDescription() {
        return "Confusion Traps randomly alter your controls for 10 steps when stepped on.";
    }
}