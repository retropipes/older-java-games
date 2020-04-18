/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;

public abstract class GenericPassThroughObject extends MazeObjectModel {
    // Constructors
    protected GenericPassThroughObject() {
        super(false);
    }

    protected GenericPassThroughObject(final boolean acceptPushInto,
            final boolean acceptPushOut, final boolean acceptPullInto,
            final boolean acceptPullOut) {
        super(false, false, acceptPushInto, acceptPushOut, false,
                acceptPullInto, acceptPullOut, true, false, 0);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundPlayer.playSound(SoundIndex.WALK, SoundGroup.GAME);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}