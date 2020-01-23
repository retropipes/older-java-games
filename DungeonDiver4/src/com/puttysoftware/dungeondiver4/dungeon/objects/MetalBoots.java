/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class MetalBoots extends AbstractBoots {
    // Constructors
    public MetalBoots() {
        super(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Metal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Metal Boots";
    }

    @Override
    public String getDescription() {
        return "Metal Boots allow Metal Buttons to be triggered. Note that you can only wear one pair of boots at once.";
    }
}
