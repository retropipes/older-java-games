/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.ArrowTypeConstants;
import com.puttysoftware.mazer5d.compatibility.abc.GenericBow;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class ShockBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public ShockBow() {
        super(ShockBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_SHOCK);
    }

    @Override
    public String getName() {
        return "Shock Bow";
    }

    @Override
    public String getPluralName() {
        return "Shock Bows";
    }

    @Override
    public String getDescription() {
        return "Shock Bows allow shooting of Shock Arrows, which energize Barrier Generators upon contact, and do everything normal arrows do.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SHOCK_BOW;
    }
}
