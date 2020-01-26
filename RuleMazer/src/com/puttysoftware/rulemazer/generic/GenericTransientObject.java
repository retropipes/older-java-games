/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.MazeConstants;

public abstract class GenericTransientObject extends MazeObject {
    // Fields
    private String name;
    private final String baseName;

    // Constructors
    protected GenericTransientObject(final String newBaseName) {
        super(true);
        this.baseName = newBaseName;
        this.name = newBaseName;
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public String getPluralName() {
        return this.name + "s";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public final void setNameSuffix(final String suffix) {
        this.name = this.baseName + " " + suffix;
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected final void setTypes() {
        // Do nothing
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
