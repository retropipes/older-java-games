/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericButton;

public class MagentaButton extends GenericButton {
    public MagentaButton() {
        super(new MagentaWallOff(), new MagentaWallOn());
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
