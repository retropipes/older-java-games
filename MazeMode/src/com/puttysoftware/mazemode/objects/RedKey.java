/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericSingleKey;

public class RedKey extends GenericSingleKey {
    // Constructors
    public RedKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Key";
    }

    @Override
    public String getPluralName() {
        return "Red Keys";
    }

    @Override
    public String getDescription() {
        return "Red Keys will unlock Red Locks, and can only be used once.";
    }
}