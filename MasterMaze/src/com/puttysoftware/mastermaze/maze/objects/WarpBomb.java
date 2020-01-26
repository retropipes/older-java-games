/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class WarpBomb extends GenericBomb {
    // Constructors
    public WarpBomb() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getName() {
        return "Warp Bomb";
    }

    @Override
    public String getPluralName() {
        return "Warp Bombs";
    }

    @Override
    public String getDescription() {
        return "Warp Bombs randomly teleport anything around the center of the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        SoundManager.playSound(SoundConstants.SOUND_EXPLODE);
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanWarpObjects(x, y, z, MazeConstants.LAYER_OBJECT,
                        GenericBomb.EFFECT_RADIUS);
    }
}