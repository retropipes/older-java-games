/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.Application;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class RoseHouse extends FinishTo {
    // Constructors
    public RoseHouse() {
        super();
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Mazer5D.getApplication();
        SoundPlayer.playSound(SoundIndex.UP, SoundGroup.GAME);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Rose House";
    }

    @Override
    public String getPluralName() {
        return "Rose Houses";
    }

    @Override
    public String getDescription() {
        return "Rose Houses send you inside when walked on.";
    }
}