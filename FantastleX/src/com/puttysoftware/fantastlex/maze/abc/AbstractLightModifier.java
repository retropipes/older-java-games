/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public abstract class AbstractLightModifier extends AbstractMazeObject {
    // Constructors
    protected AbstractLightModifier(final int tc) {
        super(true, false);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GEM;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LIGHT_MODIFIER);
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

    public static int getEffectRadius() {
        return 1;
    }
}
