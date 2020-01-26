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

public class CounterclockwiseRotationTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public CounterclockwiseRotationTrap() {
        super(ColorConstants.COLOR_YELLOW,
                ObjectImageConstants.OBJECT_IMAGE_SMALL_ROTATION,
                ColorConstants.COLOR_DARK_YELLOW);
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
            final MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        FantastleX.getApplication().showMessage("Your controls are rotated!");
        FantastleX.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE,
                CounterclockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Counterclockwise Rotation Traps rotate your controls counterclockwise for 10 steps when stepped on.";
    }
}