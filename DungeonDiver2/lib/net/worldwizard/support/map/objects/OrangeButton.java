/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericButton;
import net.worldwizard.support.map.generic.TemplateTransform;

public class OrangeButton extends GenericButton {
    public OrangeButton() {
        super(new OrangeWallOff(), new OrangeWallOn(),
                new TemplateTransform(1.0, 0.5, 0.25, ""));
    }

    @Override
    public String getName() {
        return "Orange Button";
    }

    @Override
    public String getPluralName() {
        return "Orange Buttons";
    }

    @Override
    public String getDescription() {
        return "Orange Buttons will cause all Orange Walls Off to become On, and all Orange Walls On to become Off.";
    }
}
