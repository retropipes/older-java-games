/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericInfiniteKey;

public class Tablet extends GenericInfiniteKey {
    // Constructors
    public Tablet() {
        super();
    }

    @Override
    public String getName() {
        return "Tablet";
    }

    @Override
    public String getPluralName() {
        return "Tablets";
    }

    @Override
    public String getDescription() {
        return "Tablets are used to fill Tablet Slots, and make them disappear. Tablets can be used infinitely many times.";
    }
}