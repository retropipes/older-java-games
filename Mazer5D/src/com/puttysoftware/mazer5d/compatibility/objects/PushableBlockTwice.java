/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericMovableObject;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class PushableBlockTwice extends GenericMovableObject {
    // Constructors
    public PushableBlockTwice() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block Twice";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks Twice";
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObjectModel mo,
            final int x, final int y, final int pushX, final int pushY) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        SoundPlayer.playSound(SoundIndex.PUSH_PULL, SoundGroup.GAME);
        app.getGameManager().morphOther(new PushableBlockOnce(), pushX, pushY,
                Layers.OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks Twice can only be pushed twice, before turning into a wall.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.PUSHABLE_BLOCK_TWICE;
    }
}