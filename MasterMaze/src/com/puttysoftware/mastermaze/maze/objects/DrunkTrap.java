/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTrap;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class DrunkTrap extends GenericTrap {
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
            final int dirY, final ObjectInventory inv) {
        MasterMaze.getApplication()
                .showMessage("You stumble around drunkenly!");
        MasterMaze
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