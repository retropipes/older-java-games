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
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        MazeRunnerII.getApplication().showMessage("You feel dizzy!");
        MazeRunnerII
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_DIZZY,
                        DizzinessTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_DIZZY);
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 10 steps when stepped on.";
    }
}