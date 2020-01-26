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

public class UTurnTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public UTurnTrap() {
        super(ColorConstants.COLOR_LIGHT_MAGENTA,
                ObjectImageConstants.OBJECT_IMAGE_U_TURN,
                ColorConstants.COLOR_DARK_MAGENTA);
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
            final MazeObjectInventory inv) {
        MazeRunnerII.getApplication()
                .showMessage("Your controls are turned around!");
        MazeRunnerII.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_U_TURNED, UTurnTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "U Turn Traps invert your controls for 10 steps when stepped on.";
    }
}