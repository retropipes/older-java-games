/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ArrowTypeConstants;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericBow;

public class PoisonBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public PoisonBow() {
        super(PoisonBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_POISON);
        this.setTemplateColor(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Poison Bow";
    }

    @Override
    public String getPluralName() {
        return "Poison Bows";
    }

    @Override
    public String getDescription() {
        return "Poison Bows allow shooting of Poison Arrows, which weaken Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
