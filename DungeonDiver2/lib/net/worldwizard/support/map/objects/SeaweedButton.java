/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericButton;
import net.worldwizard.support.map.generic.TemplateTransform;

public class SeaweedButton extends GenericButton {
    public SeaweedButton() {
        super(new SeaweedWallOff(), new SeaweedWallOn(),
                new TemplateTransform(0.5, 1.0, 0.5, ""));
    }

    @Override
    public String getName() {
        return "Seaweed Button";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Buttons";
    }

    @Override
    public String getDescription() {
        return "Seaweed Buttons will cause all Seaweed Walls Off to become On, and all Seaweed Walls On to become Off.";
    }
}
