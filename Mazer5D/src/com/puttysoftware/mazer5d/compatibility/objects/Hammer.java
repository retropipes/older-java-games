/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericSingleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Hammer extends GenericSingleKey {
    // Constructors
    public Hammer() {
        super();
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public String getPluralName() {
        return "Hammers";
    }

    @Override
    public String getDescription() {
        return "Hammers are used to destroy Brick Walls, and can only be used once.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.HAMMER;
    }
}