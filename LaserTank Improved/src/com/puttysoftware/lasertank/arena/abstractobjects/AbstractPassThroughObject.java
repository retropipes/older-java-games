/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena.abstractobjects;

import com.puttysoftware.lasertank.utilities.ArenaConstants;
import com.puttysoftware.lasertank.utilities.TypeConstants;

public abstract class AbstractPassThroughObject extends AbstractArenaObject {
    // Constructors
    protected AbstractPassThroughObject() {
        super(false);
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractArenaObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public int getLayer() {
        return ArenaConstants.LAYER_LOWER_OBJECTS;
    }

    @Override
    public void postMoveAction(final int dirX, final int dirY, final int dirZ) {
        // Do nothing
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}