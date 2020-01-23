/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericBoots;

public class FireBoots extends GenericBoots {
    // Constructors
    public FireBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Fire Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Fire Boots";
    }

    @Override
    public String getDescription() {
        return "Fire Boots allow walking on lava. Note that you can only wear one pair of boots at once.";
    }
}
