/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericInfiniteLock;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class TabletSlot extends GenericInfiniteLock {
    // Constructors
    public TabletSlot() {
        super(new Tablet());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Mazer5D.getBagOStuff().showMessage("You need a tablet");
        }
        SoundPlayer.playSound(SoundIndex.WALK_FAILED, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Tablet Slot";
    }

    @Override
    public String getPluralName() {
        return "Tablet Slots";
    }

    @Override
    public String getDescription() {
        return "Tablet Slots disappear when filled with a Tablet.";
    }
}