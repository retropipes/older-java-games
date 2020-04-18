/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericInventoryableObject;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class MoonStone extends GenericInventoryableObject {
    // Constants
    private static final long SCORE_GRAB_STONE = 1L;

    // Constructors
    public MoonStone() {
        super(false, 0);
    }

    @Override
    public String getName() {
        return "Moon Stone";
    }

    @Override
    public String getPluralName() {
        return "Moon Stones";
    }

    @Override
    public String getDescription() {
        return "Moon Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        inv.addItem(this);
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().decay();
        SoundPlayer.playSound(SoundIndex.SUN_STONE, SoundGroup.GAME);
        Mazer5D.getBagOStuff().getGameManager()
                .addToScore(MoonStone.SCORE_GRAB_STONE);
    }
}