/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.abc;

import com.puttysoftware.ddremix.maze.MazeConstants;
import com.puttysoftware.ddremix.maze.utilities.TypeConstants;

public abstract class AbstractItem extends AbstractMazeObject {
    // Constructors
    protected AbstractItem() {
        super(false, false);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_ITEM);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
