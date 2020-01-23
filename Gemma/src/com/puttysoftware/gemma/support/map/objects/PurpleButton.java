/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericButton;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class PurpleButton extends GenericButton {
    public PurpleButton() {
        super(new PurpleWallOff(), new PurpleWallOn(),
                new TemplateTransform(0.25, 0.125, 0.5));
    }

    @Override
    public String getName() {
        return "Purple Button";
    }

    @Override
    public String getPluralName() {
        return "Purple Buttons";
    }

    @Override
    public String getDescription() {
        return "Purple Buttons will cause all Purple Walls Off to become On, and all Purple Walls On to become Off.";
    }
}
