/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena.abstractobjects;

import com.puttysoftware.lasertank.utilities.ArenaConstants;
import com.puttysoftware.lasertank.utilities.MaterialConstants;
import com.puttysoftware.lasertank.utilities.TypeConstants;

public abstract class AbstractWall extends AbstractArenaObject {
    // Constructors
    protected AbstractWall() {
        super(true);
        this.type.set(TypeConstants.TYPE_WALL);
        this.setMaterial(MaterialConstants.MATERIAL_STONE);
    }

    @Override
    public void postMoveAction(final int dirX, final int dirY, final int dirZ) {
        // Do nothing
    }

    @Override
    public int getLayer() {
        return ArenaConstants.LAYER_LOWER_OBJECTS;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractArenaObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public boolean doLasersPassThrough() {
        return false;
    }
}