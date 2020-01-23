/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericButton;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class SkyButton extends GenericButton {
    public SkyButton() {
        super(new SkyWallOff(), new SkyWallOn(), new TemplateTransform(0.5,
                0.5, 1.0));
    }

    @Override
    public String getName() {
        return "Sky Button";
    }

    @Override
    public String getPluralName() {
        return "Sky Buttons";
    }

    @Override
    public String getDescription() {
        return "Sky Buttons will cause all Sky Walls Off to become On, and all Sky Walls On to become Off.";
    }
}
