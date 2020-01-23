/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericSingleKey;

public class BlueKey extends GenericSingleKey {
    // Constructors
    public BlueKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Key";
    }

    @Override
    public String getPluralName() {
        return "Blue Keys";
    }

    @Override
    public String getDescription() {
        return "Blue Keys will unlock Blue Locks, and can only be used once.";
    }
}