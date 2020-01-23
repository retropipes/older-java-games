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

public class ArrowTrap extends AbstractTrap {
    // Constructors
    public ArrowTrap() {
        super(ColorConstants.COLOR_ORANGE,
                ObjectImageConstants.OBJECT_IMAGE_ARROW,
                ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Arrow Trap";
    }

    @Override
    public String getPluralName() {
        return "Arrow Traps";
    }

    @Override
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, MazeObjectInventory inv) {
        MazeRunnerII.getApplication().showMessage("The arrow is stopped!");
        return false;
    }

    @Override
    public String getDescription() {
        return "Arrow Traps stop arrows.";
    }
}