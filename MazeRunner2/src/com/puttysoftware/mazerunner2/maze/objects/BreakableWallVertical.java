/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractWall;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class BreakableWallVertical extends AbstractWall {
    // Constructors
    public BreakableWallVertical() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_BREAKABLE_VERTICAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Breakable Wall Vertical";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Vertical";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Vertical break up into nothing if walked into, and propagate the effect to other like walls.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        int dirZ = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        this.chainReactionAction(dirX, dirY, dirZ);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Break up
        MazeRunnerII
                .getApplication()
                .getGameManager()
                .morph(new Empty(), dirX, dirY, dirZ,
                        MazeConstants.LAYER_OBJECT);
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        AbstractMazeObject above = m.getCell(dirX, dirY - 1, dirZ,
                MazeConstants.LAYER_OBJECT);
        try {
            if (above.isOfType(TypeConstants.TYPE_BREAKABLE_V)) {
                this.chainReactionAction(dirX, dirY - 1, dirZ);
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
        try {
            AbstractMazeObject below = m.getCell(dirX, dirY + 1, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (below.isOfType(TypeConstants.TYPE_BREAKABLE_V)) {
                this.chainReactionAction(dirX, dirY + 1, dirZ);
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
        this.type.set(TypeConstants.TYPE_BREAKABLE_V);
    }
}