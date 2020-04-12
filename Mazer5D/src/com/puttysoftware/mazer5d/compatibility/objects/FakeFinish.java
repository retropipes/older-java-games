/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericPassThroughObject;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class FakeFinish extends GenericPassThroughObject {
    // Constructors
    public FakeFinish() {
        super();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundPlayer.playSound(SoundIndex.WALK_FAILED,
                SoundGroup.GAME);
        Mazer5D.getApplication().showMessage("Fake exit!");
    }

    @Override
    public String getName() {
        return "Fake Finish";
    }

    @Override
    public String getGameName() {
        return "Finish";
    }

    @Override
    public String getPluralName() {
        return "Fake Finishes";
    }

    @Override
    public String getDescription() {
        return "Fake Finishes look like regular finishes but don't lead anywhere.";
    }
}