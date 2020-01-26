/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericUsableObject;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class PoisonBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 2;

    // Constructors
    public PoisonBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Poison Bomb";
    }

    @Override
    public String getPluralName() {
        return "Poison Bombs";
    }

    @Override
    public String getDescription() {
        return "Poison Bombs poison anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Destroy bomb
        WeaselWeb.getApplication().getGameManager().morph(new Empty(), locX,
                locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_EXPLODE);
        // Poison objects that react to poison
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .radialScanPoisonObjects(x, y, z, PoisonBomb.EFFECT_RADIUS);
        // Poison the ground, too
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .radialScanPoisonGround(x, y, z, PoisonBomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }
}
