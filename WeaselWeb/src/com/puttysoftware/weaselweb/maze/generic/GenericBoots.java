/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericBoots extends GenericPass {
    // Constructors
    protected GenericBoots() {
        super();
        this.setType(TypeConstants.TYPE_BOOTS);
    }

    @Override
    public abstract String getName();

    public final String getBootsName() {
        final String name = this.getName();
        return name.substring(0, name.length() - 6);
    }
}
