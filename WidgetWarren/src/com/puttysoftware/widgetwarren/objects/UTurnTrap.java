/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTrap;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class UTurnTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public UTurnTrap() {
        super();
    }

    @Override
    public String getName() {
        return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
        return "U Turn Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        WidgetWarren.getApplication()
                .showMessage("Your controls are turned around!");
        WidgetWarren.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_U_TURNED, UTurnTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "U Turn Traps invert your controls for 10 steps when stepped on.";
    }
}