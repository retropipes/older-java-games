/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.ArrowTypeConstants;
import com.puttysoftware.mazer5d.compatibility.abc.GenericBow;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class GhostBow extends GenericBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public GhostBow() {
        super(GhostBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_GHOST);
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

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.GHOST_BOW;
    }
}
