/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ArrowTypeConstants;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericBow;

public class GhostBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public GhostBow() {
        super(GhostBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_GHOST);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Ghost Bow";
    }

    @Override
    public String getPluralName() {
        return "Ghost Bows";
    }

    @Override
    public String getDescription() {
        return "Ghost Bows allow shooting of Ghost Arrows, which pass through objects that do not react to arrows, even if they are solid, and do everything normal arrows do.";
    }
}
