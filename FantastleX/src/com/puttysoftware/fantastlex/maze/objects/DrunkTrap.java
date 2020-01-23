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

public class DrunkTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DrunkTrap() {
        super(ColorConstants.COLOR_LIGHT_PURPLE,
                ObjectImageConstants.OBJECT_IMAGE_DRUNK,
                ColorConstants.COLOR_PURPLE);
    }

    @Override
    public String getName() {
        return "Drunk Trap";
    }

    @Override
    public String getPluralName() {
        return "Drunk Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        FantastleX.getApplication()
                .showMessage("You stumble around drunkenly!");
        FantastleX
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_DRUNK,
                        DrunkTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_DRUNK);
    }

    @Override
    public String getDescription() {
        return "Drunk Traps alter your movement in a way that resembles being intoxicated for 10 steps when stepped on.";
    }
}