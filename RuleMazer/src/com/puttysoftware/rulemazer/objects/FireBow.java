/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.ArrowTypeConstants;
import com.puttysoftware.rulemazer.generic.GenericBow;

public class FireBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public FireBow() {
        super(FireBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_FIRE);
    }

    @Override
    public String getName() {
        return "Fire Bow";
    }

    @Override
    public String getPluralName() {
        return "Fire Bows";
    }

    @Override
    public String getDescription() {
        return "Fire Bows allow shooting of Fire Arrows, which burn Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
