/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericMovableObject;

public class PullableBlock extends GenericMovableObject {
    // Constructors
    public PullableBlock() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks can only be pulled, not pushed.";
    }
}