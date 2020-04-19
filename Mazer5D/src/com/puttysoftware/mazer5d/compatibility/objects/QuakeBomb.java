/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericUsableObject;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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
        Mazer5D.getBagOStuff().getGameManager().morph(GameObjects
                .getEmptySpace(), locX, locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final MazeObjectModel mo, final int x, final int y,
            final int z) {
        SoundPlayer.playSound(SoundIndex.EXPLODE, SoundGroup.GAME);
        // Earthquake
        Mazer5D.getBagOStuff().getMazeManager().getMaze().radialScanQuakeBomb(x,
                y, z, QuakeBomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.QUAKE_BOMB;
    }
}
