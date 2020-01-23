/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericButton;

public class RedButton extends GenericButton {
    public RedButton() {
        super(new RedWallOff(), new RedWallOn());
    }

    @Override
    public String getName() {
        return "Red Button";
    }

    @Override
    public String getPluralName() {
        return "Red Buttons";
    }

    @Override
    public String getDescription() {
        return "Red Buttons will cause all Red Walls Off to become On, and all Red Walls On to become Off.";
    }
}
