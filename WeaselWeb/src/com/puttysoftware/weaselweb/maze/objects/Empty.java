/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericPassThroughObject;
import com.puttysoftware.weaselweb.maze.generic.TypeConstants;

public class Empty extends GenericPassThroughObject {
    // Constructors
    public Empty() {
        super(true, true, true, true);
        this.setType(TypeConstants.TYPE_EMPTY_SPACE);
    }

    @Override
    public String getName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Squares of Emptiness";
    }

    @Override
    public String getDescription() {
        return "Squares of Emptiness are what fills areas that aren't occupied by other objects.";
    }
}