/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public abstract class GenericPassThroughObject extends MazeObject {
    // Constructors
    protected GenericPassThroughObject() {
        super();
        this.setType(TypeConstants.TYPE_PASS_THROUGH);
    }

    protected GenericPassThroughObject(final boolean acceptPushInto,
            final boolean acceptPushOut, final boolean acceptPullInto,
            final boolean acceptPullOut) {
        super();
        this.setPushableInto(acceptPushInto);
        this.setPushableOut(acceptPushOut);
        this.setPullableInto(acceptPullInto);
        this.setPullableOut(acceptPullOut);
        this.setType(TypeConstants.TYPE_PASS_THROUGH);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}