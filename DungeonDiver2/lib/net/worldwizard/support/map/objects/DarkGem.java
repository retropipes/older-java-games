/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericLightModifier;
import net.worldwizard.support.map.generic.TemplateTransform;

public class DarkGem extends GenericLightModifier {
    // Constructors
    public DarkGem() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.2, 0.2, 0.2, ""));
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

    @Override
    public String getGameImageNameHook() {
        return "gem";
    }
}
