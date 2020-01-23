/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.ArrowTypeConstants;
import com.puttysoftware.mazer5d.generic.GenericBow;

public class IceBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public IceBow() {
        super(IceBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_ICE);
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
