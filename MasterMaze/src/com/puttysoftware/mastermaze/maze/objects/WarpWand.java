/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWand;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class WarpWand extends GenericWand {
    public WarpWand() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Warp Wand";
    }

    @Override
    public String getPluralName() {
        return "Warp Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = MasterMaze.getApplication();
        app.getMazeManager()
                .getMaze()
                .warpObject(
                        app.getMazeManager().getMaze()
                                .getCell(x, y, z, MazeConstants.LAYER_OBJECT),
                        x, y, z, MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Warp Wands will teleport the object at the target square to a random location when used.";
    }
}
