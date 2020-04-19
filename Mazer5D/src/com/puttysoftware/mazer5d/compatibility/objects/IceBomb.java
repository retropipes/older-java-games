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

public class IceBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 2;

    // Constructors
    public IceBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Ice Bomb";
    }

    @Override
    public String getPluralName() {
        return "Ice Bombs";
    }

    @Override
    public String getDescription() {
        return "Ice Bombs freeze anything in an area of radius 2 centered on the target point.";
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
        // Freeze objects that react to ice
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .radialScanFreezeObjects(x, y, z, IceBomb.EFFECT_RADIUS);
        // Freeze ground, too
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .radialScanFreezeGround(x, y, z, IceBomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.ICE_BOMB;
    }
}
