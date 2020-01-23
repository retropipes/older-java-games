/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericButton;
import net.worldwizard.support.map.generic.TemplateTransform;

public class SkyButton extends GenericButton {
    public SkyButton() {
        super(new SkyWallOff(), new SkyWallOn(), new TemplateTransform(0.5,
                0.5, 1.0, ""));
    }

    @Override
    public String getName() {
        return "Sky Button";
    }

    @Override
    public String getPluralName() {
        return "Sky Buttons";
    }

    @Override
    public String getDescription() {
        return "Sky Buttons will cause all Sky Walls Off to become On, and all Sky Walls On to become Off.";
    }
}
