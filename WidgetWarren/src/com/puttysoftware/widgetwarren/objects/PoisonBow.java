/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.ArrowTypeConstants;
import com.puttysoftware.widgetwarren.generic.GenericBow;

public class PoisonBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public PoisonBow() {
        super(PoisonBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_POISON);
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
