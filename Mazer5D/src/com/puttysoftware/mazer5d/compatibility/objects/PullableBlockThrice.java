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
import com.puttysoftware.mazer5d.compatibility.maze.MazeConstants;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class PullableBlockThrice extends GenericMovableObject {
    // Constructors
    public PullableBlockThrice() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block Thrice";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Thrice";
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObjectModel mo,
            final int x, final int y, final int pushX, final int pushY) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        SoundPlayer.playSound(SoundIndex.PUSH_PULL, SoundGroup.GAME);
        app.getGameManager().morphOther(new PullableBlockTwice(), pushX, pushY,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Thrice can only be pulled three times, before turning into a wall.";
    }
}