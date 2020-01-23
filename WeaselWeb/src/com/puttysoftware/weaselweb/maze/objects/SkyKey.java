/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericSingleKey;

public class SkyKey extends GenericSingleKey {
    // Constructors
    public SkyKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Key";
    }

    @Override
    public String getPluralName() {
        return "Sky Keys";
    }

    @Override
    public String getDescription() {
        return "Sky Keys will unlock Sky Locks, and can only be used once.";
    }
}