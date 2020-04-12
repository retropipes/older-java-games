/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.generic.GenericSingleLock;
import com.puttysoftware.mazer5d.loaders.SoundConstants;
import com.puttysoftware.mazer5d.loaders.SoundManager;

public class GreenLock extends GenericSingleLock {
    // Constructors
    public GreenLock() {
        super(new GreenKey());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Mazer5D.getApplication().showMessage("You need a green key");
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "Green Lock";
    }

    @Override
    public String getPluralName() {
        return "Green Locks";
    }

    @Override
    public String getDescription() {
        return "Green Locks require Green Keys to open.";
    }
}