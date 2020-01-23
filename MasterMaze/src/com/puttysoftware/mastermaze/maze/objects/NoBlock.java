/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericAntiObject;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class NoBlock extends GenericAntiObject {
    // Constructors
    public NoBlock() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_NO);
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        // Destroy incoming block
        final Application app = MasterMaze.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public void pullIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        // Destroy incoming block
        final Application app = MasterMaze.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getName() {
        return "No Block";
    }

    @Override
    public String getPluralName() {
        return "No Blocks";
    }

    @Override
    public String getDescription() {
        return "No Blocks destroy any blocks that attempt to pass through.";
    }
}