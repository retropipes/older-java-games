/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericLightModifier;

public class DarkGem extends GenericLightModifier {
    // Constructors
    public DarkGem() {
        super();
    }

    @Override
    public String getName() {
        return "Dark Gem";
    }

    @Override
    public String getPluralName() {
        return "Dark Gems";
    }

    @Override
    public String getDescription() {
        return "Dark Gems shroud the immediately adjacent area in permanent darkness.";
    }
}
