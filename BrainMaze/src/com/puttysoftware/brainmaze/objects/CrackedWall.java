/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericWall;
import com.puttysoftware.brainmaze.generic.TypeConstants;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class CrackedWall extends GenericWall {
    // Constructors
    public CrackedWall() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeName("cracked");
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Cracked Wall";
    }

    @Override
    public String getPluralName() {
        return "Cracked Walls";
    }

    @Override
    public String getDescription() {
        return "Cracked Walls break up into Damaged Walls if walked into.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final int z = BrainMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        BrainMaze
                .getApplication()
                .getGameManager()
                .morph(new DamagedWall(), dirX, dirY, z,
                        MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }
}