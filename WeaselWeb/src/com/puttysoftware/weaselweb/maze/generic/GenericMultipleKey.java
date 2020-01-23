/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericMultipleKey extends GenericKey {
    // Constructors
    protected GenericMultipleKey() {
        super(true);
        this.setType(TypeConstants.TYPE_MULTIPLE_KEY);
    }
}