/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public abstract class AbstractBow extends AbstractUsableObject {
    // Fields
    private final int AT;

    // Constructors
    protected AbstractBow(final int uses, final int arrowType, final int tc) {
        super(uses);
        this.AT = arrowType;
        this.setTemplateColor(tc);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        // Do nothing
    }

    @Override
    public void useAction(final AbstractMazeObject mo, final int x,
            final int y, final int z) {
        // Do nothing
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOW;
    }

    @Override
    public abstract String getName();

    public int getArrowType() {
        return this.AT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOW);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
