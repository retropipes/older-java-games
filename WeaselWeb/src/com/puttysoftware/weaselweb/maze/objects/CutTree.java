/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericPassThroughObject;

public class CutTree extends GenericPassThroughObject {
    // Constructors
    public CutTree() {
        super();
    }

    @Override
    public String getName() {
        return "Cut Tree";
    }

    @Override
    public String getPluralName() {
        return "Cut Trees";
    }

    @Override
    public String getDescription() {
        return "Cut Trees are the leftover stubs of Trees that have been cut by an Axe.";
    }
}