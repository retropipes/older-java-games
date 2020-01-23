/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericSingleKey;

public class CyanKey extends GenericSingleKey {
    // Constructors
    public CyanKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Key";
    }

    @Override
    public String getPluralName() {
        return "Cyan Keys";
    }

    @Override
    public String getDescription() {
        return "Cyan Keys will unlock Cyan Locks, and can only be used once.";
    }
}