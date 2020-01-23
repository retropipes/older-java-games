/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericButton;

public class WhiteButton extends GenericButton {
    public WhiteButton() {
        super(new WhiteWallOff(), new WhiteWallOn());
    }

    @Override
    public String getName() {
        return "White Button";
    }

    @Override
    public String getPluralName() {
        return "White Buttons";
    }

    @Override
    public String getDescription() {
        return "White Buttons will cause all White Walls Off to become On, and all White Walls On to become Off.";
    }
}
