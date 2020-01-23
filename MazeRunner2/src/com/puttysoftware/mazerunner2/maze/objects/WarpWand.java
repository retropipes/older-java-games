/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractWand;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class WarpWand extends AbstractWand {
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
    public void useAction(final AbstractMazeObject mo, final int x,
            final int y, final int z) {
        Application app = MazeRunnerII.getApplication();
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
