/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericSingleKey;

public class YellowKey extends GenericSingleKey {
    // Constructors
    public YellowKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Key";
    }

    @Override
    public String getPluralName() {
        return "Yellow Keys";
    }

    @Override
    public String getDescription() {
        return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}