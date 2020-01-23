/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericButton;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class MagentaButton extends GenericButton {
    public MagentaButton() {
        super(new MagentaWallOff(), new MagentaWallOn(),
                new TemplateTransform(1.0, 0.0, 1.0));
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
