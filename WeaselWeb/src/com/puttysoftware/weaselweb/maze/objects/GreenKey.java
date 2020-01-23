/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericSingleKey;

public class GreenKey extends GenericSingleKey {
    // Constructors
    public GreenKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Key";
    }

    @Override
    public String getPluralName() {
        return "Green Keys";
    }

    @Override
    public String getDescription() {
        return "Green Keys will unlock Green Locks, and can only be used once.";
    }
}