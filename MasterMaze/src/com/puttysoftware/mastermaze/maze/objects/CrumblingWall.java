/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class CrumblingWall extends GenericWall {
    // Constructors
    public CrumblingWall() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_CRUMBLING);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Crumbling Wall";
    }

    @Override
    public String getPluralName() {
        return "Crumbling Walls";
    }

    @Override
    public String getDescription() {
        return "Crumbling Walls break up into nothing if walked into.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final int z = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MasterMaze.getApplication().getGameManager().morph(new Empty(), dirX,
                dirY, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }
}