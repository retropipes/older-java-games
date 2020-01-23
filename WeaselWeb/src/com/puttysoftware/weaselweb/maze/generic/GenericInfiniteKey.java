/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericInfiniteKey extends GenericKey {
    // Constructors
    protected GenericInfiniteKey() {
        super(true);
        this.setType(TypeConstants.TYPE_INFINITE_KEY);
    }

    // Scriptability
    @Override
    public abstract String getName();
}