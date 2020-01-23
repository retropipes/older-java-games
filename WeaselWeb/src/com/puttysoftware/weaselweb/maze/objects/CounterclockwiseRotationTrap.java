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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        WeaselWeb.getApplication().showMessage("Your controls are rotated!");
        WeaselWeb
                .getApplication()
                .getGameManager()
                .activateEffect(
                        MazeEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE,
                        CounterclockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Counterclockwise Rotation Traps rotate your controls counterclockwise for 10 steps when stepped on.";
    }
}