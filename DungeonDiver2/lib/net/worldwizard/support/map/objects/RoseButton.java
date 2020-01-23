/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericButton;
import net.worldwizard.support.map.generic.TemplateTransform;

public class RoseButton extends GenericButton {
    public RoseButton() {
        super(new RoseWallOff(), new RoseWallOn(), new TemplateTransform(1.0,
                0.5, 0.5, ""));
    }

    @Override
    public String getName() {
        return "Rose Button";
    }

    @Override
    public String getPluralName() {
        return "Rose Buttons";
    }

    @Override
    public String getDescription() {
        return "Rose Buttons will cause all Rose Walls Off to become On, and all Rose Walls On to become Off.";
    }
}
