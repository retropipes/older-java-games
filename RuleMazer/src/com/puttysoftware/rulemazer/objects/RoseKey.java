/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericSingleKey;

public class RoseKey extends GenericSingleKey {
    // Constructors
    public RoseKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Key";
    }

    @Override
    public String getPluralName() {
        return "Rose Keys";
    }

    @Override
    public String getDescription() {
        return "Rose Keys will unlock Rose Locks, and can only be used once.";
    }
}