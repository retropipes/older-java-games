/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericButton;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class WhiteButton extends GenericButton {
    public WhiteButton() {
        super(new WhiteWallOff(), new WhiteWallOn(),
                new TemplateTransform(1.0, 1.0, 1.0));
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
