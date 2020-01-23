/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

public abstract class GenericCheckKey extends GenericInventoryableObject {
    // Constructors
    protected GenericCheckKey() {
        super();
    }

    protected GenericCheckKey(final int templateColor) {
        super();
        this.setTemplateColor(templateColor);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_CHECK_KEY);
    }
}
