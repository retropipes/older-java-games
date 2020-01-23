/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericButton;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class CyanButton extends GenericButton {
    public CyanButton() {
        super(new CyanWallOff(), new CyanWallOn(), new TemplateTransform(0.0,
                1.0, 1.0));
    }

    @Override
    public String getName() {
        return "Cyan Button";
    }

    @Override
    public String getPluralName() {
        return "Cyan Buttons";
    }

    @Override
    public String getDescription() {
        return "Cyan Buttons will cause all Cyan Walls Off to become On, and all Cyan Walls On to become Off.";
    }
}
