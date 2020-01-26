/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class DamageableWall extends AbstractWall {
    // Constructors
    public DamageableWall() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_DAMAGEABLE);
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
            final int dirY, final MazeObjectInventory inv) {
        final int z = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MazeRunnerII.getApplication().getGameManager().morph(new CrackedWall(),
                dirX, dirY, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }
}