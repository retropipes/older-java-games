/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class AquaBoots extends AbstractBoots {
    // Constructors
    public AquaBoots() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Aqua Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Aqua Boots";
    }

    @Override
    public String getDescription() {
        return "Aqua Boots allow walking on water. Note that you can only wear one pair of boots at once.";
    }
}
