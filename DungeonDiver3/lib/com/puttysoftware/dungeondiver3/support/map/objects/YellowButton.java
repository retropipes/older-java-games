/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericButton;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class YellowButton extends GenericButton {
    public YellowButton() {
        super(new YellowWallOff(), new YellowWallOn(),
                new TemplateTransform(1.0, 1.0, 0.0));
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
