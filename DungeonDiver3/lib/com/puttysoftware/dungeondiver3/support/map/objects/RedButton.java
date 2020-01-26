/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericButton;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class RedButton extends GenericButton {
    public RedButton() {
        super(new RedWallOff(), new RedWallOn(),
                new TemplateTransform(1.0, 0.0, 0.0));
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
