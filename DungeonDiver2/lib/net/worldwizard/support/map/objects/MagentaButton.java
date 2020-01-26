/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericButton;
import net.worldwizard.support.map.generic.TemplateTransform;

public class MagentaButton extends GenericButton {
    public MagentaButton() {
        super(new MagentaWallOff(), new MagentaWallOn(),
                new TemplateTransform(1.0, 0.0, 1.0, ""));
    }

    @Override
    public String getName() {
        return "Magenta Button";
    }

    @Override
    public String getPluralName() {
        return "Magenta Buttons";
    }

    @Override
    public String getDescription() {
        return "Magenta Buttons will cause all Magenta Walls Off to become On, and all Magenta Walls On to become Off.";
    }
}
