/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.Maze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class BreakableWallHorizontal extends GenericWall {
    // Constructors
    public BreakableWallHorizontal() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(
                ObjectImageConstants.OBJECT_IMAGE_BREAKABLE_HORIZONTAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Breakable Wall Horizontal";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Horizontal";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Horizontal break up into nothing if walked into, and propagate the effect to other like walls.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final int dirZ = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        this.chainReactionAction(dirX, dirY, dirZ);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Break up
        MasterMaze.getApplication().getGameManager().morph(new Empty(), dirX,
                dirY, dirZ, MazeConstants.LAYER_OBJECT);
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        try {
            final MazeObject left = m.getCell(dirX - 1, dirY, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (left.isOfType(TypeConstants.TYPE_BREAKABLE_H)) {
                this.chainReactionAction(dirX - 1, dirY, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
        try {
            final MazeObject right = m.getCell(dirX + 1, dirY, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (right.isOfType(TypeConstants.TYPE_BREAKABLE_H)) {
                this.chainReactionAction(dirX + 1, dirY, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
        this.type.set(TypeConstants.TYPE_BREAKABLE_H);
    }
}