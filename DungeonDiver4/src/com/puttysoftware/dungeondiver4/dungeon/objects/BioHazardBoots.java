/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class BioHazardBoots extends AbstractBoots {
    // Constructors
    public BioHazardBoots() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getName() {
        return "Bio-Hazard Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Bio-Hazard Boots";
    }

    @Override
    public String getDescription() {
        return "Bio-Hazard Boots allow walking on slime. Note that you can only wear one pair of boots at once.";
    }
}
