/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractWand;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

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
        final Application app = FantastleX.getApplication();
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
