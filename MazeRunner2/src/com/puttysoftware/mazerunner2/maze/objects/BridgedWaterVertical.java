/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractGround;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class BridgedWaterVertical extends AbstractGround {
    // Constructors
    public BridgedWaterVertical() {
        super(ColorConstants.COLOR_WATER);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_BRIDGE_VERTICAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public String getName() {
        return "Bridged Water Vertical";
    }

    @Override
    public String getPluralName() {
        return "Squares of Bridged Water Vertical";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Bridged Water Vertical, unlike Water, can be walked on.";
    }
}
