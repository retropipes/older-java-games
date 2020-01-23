/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTrap;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class WallMakingTrap extends AbstractTrap {
    public WallMakingTrap() {
        super(ColorConstants.COLOR_BRIDGE,
                ObjectImageConstants.OBJECT_IMAGE_WALL_MAKING,
                ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Wall-Making Trap";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Traps";
    }

    @Override
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
        MazeRunnerII.getApplication().getGameManager()
                .delayedDecayTo(new Wall());
    }

    @Override
    public String getDescription() {
        return "Wall-Making Traps create a Wall when you step OFF them.";
    }
}
