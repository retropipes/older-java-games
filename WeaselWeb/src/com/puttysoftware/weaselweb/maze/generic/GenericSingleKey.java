/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericSingleKey extends GenericKey {
    // Constructors
    protected GenericSingleKey() {
        super(false);
        this.setType(TypeConstants.TYPE_SINGLE_KEY);
    }
}