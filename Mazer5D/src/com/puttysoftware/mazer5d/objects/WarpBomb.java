/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericUsableObject;
import com.puttysoftware.mazer5d.generic.MazeObject;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class WarpBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;

    // Constructors
    public WarpBomb() {
        super(1);
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
        return "Warp Bombs randomly teleport anything in an area of radius 3 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        // Destroy bomb
        Mazer5D.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        // Warp objects
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_EXPLODE);
        Mazer5D.getApplication()
                .getMazeManager()
                .getMaze()
                .radialScanWarpObjects(x, y, z, MazeConstants.LAYER_OBJECT,
                        WarpBomb.EFFECT_RADIUS);
        // Player might have moved
        Mazer5D.getApplication().getGameManager().findPlayerAndAdjust();
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }
}