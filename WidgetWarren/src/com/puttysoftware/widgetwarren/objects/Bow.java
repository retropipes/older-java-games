/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.ArrowTypeConstants;
import com.puttysoftware.widgetwarren.generic.GenericBow;

public class Bow extends GenericBow {
    // Constants
    private static final int BOW_USES = -1;

    // Constructors
    public Bow() {
        super(Bow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_PLAIN);
    }

    @Override
    public String getName() {
        return "Bow";
    }

    @Override
    public String getPluralName() {
        return "Bows";
    }

    @Override
    public String getDescription() {
        return "Bows shoot an unlimited supply of normal arrows.";
    }
}
