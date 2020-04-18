/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericSingleLock;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class SeaweedLock extends GenericSingleLock {
    // Constructors
    public SeaweedLock() {
        super(new SeaweedKey());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Mazer5D.getBagOStuff().showMessage("You need a seaweed key");
        }
        SoundPlayer.playSound(SoundIndex.WALK_FAILED, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Seaweed Lock";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Locks";
    }

    @Override
    public String getDescription() {
        return "Seaweed Locks require Seaweed Keys to open.";
    }
}