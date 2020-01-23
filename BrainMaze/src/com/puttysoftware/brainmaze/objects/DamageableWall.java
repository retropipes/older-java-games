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

public class DamageableWall extends GenericWall {
    // Constructors
    public DamageableWall() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeName("damageable");
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Damageable Wall";
    }

    @Override
    public String getPluralName() {
        return "Damageable Walls";
    }

    @Override
    public String getDescription() {
        return "Damageable Walls break up into Cracked Walls if walked into.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final int z = BrainMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        BrainMaze
                .getApplication()
                .getGameManager()
                .morph(new CrackedWall(), dirX, dirY, z,
                        MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }
}