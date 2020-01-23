/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericAntiObject;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class NoBlock extends GenericAntiObject {
    // Constructors
    public NoBlock() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
        this.setAttributeName("no");
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public final String getBaseName() {
        return "block_base";
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        // Destroy incoming block
        final Application app = BrainMaze.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FALL_INTO_PIT);
    }

    @Override
    public void pullIntoAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int z) {
        // Destroy incoming block
        final Application app = BrainMaze.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FALL_INTO_PIT);
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