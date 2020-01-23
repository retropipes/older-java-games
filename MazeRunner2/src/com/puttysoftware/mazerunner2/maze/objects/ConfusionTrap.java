/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTrap;
import com.puttysoftware.mazerunner2.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

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
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        MazeRunnerII.getApplication().showMessage("You are confused!");
        MazeRunnerII
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_CONFUSED,
                        ConfusionTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_CONFUSED);
    }

    @Override
    public String getDescription() {
        return "Confusion Traps randomly alter your controls for 10 steps when stepped on.";
    }
}