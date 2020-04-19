/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMovableObject;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class PushableBlock extends GenericMovableObject {
    // Constructors
    public PushableBlock() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks can only be pushed, not pulled.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.PUSHABLE_BLOCK;
    }
}