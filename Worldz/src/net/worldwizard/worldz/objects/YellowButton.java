/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericButton;

public class YellowButton extends GenericButton {
    public YellowButton() {
        super(new YellowWallOff(), new YellowWallOn());
    }

    @Override
    public String getName() {
        return "Yellow Button";
    }

    @Override
    public String getPluralName() {
        return "Yellow Buttons";
    }

    @Override
    public String getDescription() {
        return "Yellow Buttons will cause all Yellow Walls Off to become On, and all Yellow Walls On to become Off.";
    }
}
