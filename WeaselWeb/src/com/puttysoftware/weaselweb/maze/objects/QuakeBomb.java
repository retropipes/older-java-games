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

public class QuakeBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;

    // Constructors
    public QuakeBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Quake Bomb";
    }

    @Override
    public String getPluralName() {
        return "Quake Bombs";
    }

    @Override
    public String getDescription() {
        return "Quake Bombs crack plain and one-way walls and may also cause crevasses to form when used; they act on an area of radius 3.";
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
        // Earthquake
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .radialScanQuakeBomb(x, y, z, QuakeBomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }
}
