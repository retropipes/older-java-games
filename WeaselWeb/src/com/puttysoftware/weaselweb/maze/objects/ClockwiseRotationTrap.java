/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericTrap;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class ClockwiseRotationTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ClockwiseRotationTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Clockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        WeaselWeb.getApplication().showMessage("Your controls are rotated!");
        WeaselWeb.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_ROTATED_CLOCKWISE,
                ClockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 10 steps when stepped on.";
    }
}