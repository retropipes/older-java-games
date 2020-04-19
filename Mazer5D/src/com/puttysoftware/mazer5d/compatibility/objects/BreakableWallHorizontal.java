/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWall;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.compatibility.abc.TypeConstants;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class BreakableWallHorizontal extends GenericWall {
    // Constructors
    public BreakableWallHorizontal() {
        super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        SoundPlayer.playSound(SoundIndex.CRACK, SoundGroup.GAME);
        BreakableWallHorizontal.doChainReact(x, y, z);
    }

    private static void doChainReact(final int x, final int y, final int z) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        BreakableWallHorizontal curr = null;
        try {
            curr = (BreakableWallHorizontal) app.getMazeManager().getMazeObject(
                    x, y, z, Layers.OBJECT);
        } catch (final ClassCastException cce) {
            // We're not a breakable wall horizontal, so abort
            return;
        }
        String mo4Name, mo6Name, invalidName, currName;
        invalidName = new Bounds().getName();
        currName = curr.getName();
        final MazeObjectModel mo4 = app.getMazeManager().getMazeObject(x - 1, y,
                z, Layers.OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        }
        final MazeObjectModel mo6 = app.getMazeManager().getMazeObject(x + 1, y,
                z, Layers.OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        }
        app.getGameManager().morph(GameObjects.getEmptySpace(), x, y, z);
        if (mo4Name.equals(currName)) {
            BreakableWallHorizontal.doChainReact(x - 1, y, z);
        }
        if (mo6Name.equals(currName)) {
            BreakableWallHorizontal.doChainReact(x + 1, y, z);
        }
    }

    @Override
    public String getName() {
        return "Breakable Wall Horizontal";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Horizontal";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Horizontal disintegrate when touched, causing other Breakable Walls Horizontal nearby to also disintegrate.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BREAKABLE_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.BREAKABLE_WALL_HORIZONTAL;
    }
}
