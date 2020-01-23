/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericPass extends GenericInfiniteKey {
    // Constructors
    protected GenericPass() {
        super();
        this.setType(TypeConstants.TYPE_PASS);
    }

    @Override
    public abstract String getName();
}
