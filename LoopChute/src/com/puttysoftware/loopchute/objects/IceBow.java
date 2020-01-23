/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ArrowTypeConstants;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericBow;

public class IceBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public IceBow() {
        super(IceBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_ICE);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Ice Bow";
    }

    @Override
    public String getPluralName() {
        return "Ice Bows";
    }

    @Override
    public String getDescription() {
        return "Ice Bows allow shooting of Ice Arrows, which freeze Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
