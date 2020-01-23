/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractLightModifier;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class LightGem extends AbstractLightModifier {
    // Constructors
    public LightGem() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public String getName() {
        return "Light Gem";
    }

    @Override
    public String getPluralName() {
        return "Light Gems";
    }

    @Override
    public String getDescription() {
        return "Light Gems bathe the immediately adjacent area in permanent light.";
    }
}
