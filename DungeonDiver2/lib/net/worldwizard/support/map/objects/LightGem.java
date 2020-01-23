/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericLightModifier;

public class LightGem extends GenericLightModifier {
    // Constructors
    public LightGem() {
        super();
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

    @Override
    public String getGameImageNameHook() {
        return "gem";
    }
}
