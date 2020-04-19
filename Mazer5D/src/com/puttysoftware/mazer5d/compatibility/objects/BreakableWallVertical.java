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
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class BreakableWallVertical extends GenericWall {
    // Constructors
    public BreakableWallVertical() {
        super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        SoundPlayer.playSound(SoundIndex.CRACK, SoundGroup.GAME);
        this.doChainReact(x, y, z);
    }

    public void doChainReact(final int x, final int y, final int z) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        BreakableWallVertical curr = null;
        try {
            curr = (BreakableWallVertical) app.getMazeManager().getMazeObject(x,
                    y, z, Layers.OBJECT);
        } catch (final ClassCastException cce) {
            // We're not a breakable wall vertical, so abort
            return;
        }
        String mo2Name, mo8Name, invalidName, currName;
        invalidName = new Bounds().getName();
        currName = curr.getName();
        final MazeObjectModel mo2 = app.getMazeManager().getMazeObject(x, y - 1,
                z, Layers.OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        }
        final MazeObjectModel mo8 = app.getMazeManager().getMazeObject(x, y + 1,
                z, Layers.OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        }
        app.getGameManager().morph(GameObjects.getEmptySpace(), x, y, z);
        if (mo2Name.equals(currName)) {
            curr.doChainReact(x, y - 1, z);
        }
        if (mo8Name.equals(currName)) {
            curr.doChainReact(x, y + 1, z);
        }
    }

    @Override
    public String getName() {
        return "Breakable Wall Vertical";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Vertical";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Vertical disintegrate when touched, causing other Breakable Walls Vertical nearby to also disintegrate.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.BREAKABLE_WALL_VERTICAL;
    }
}
