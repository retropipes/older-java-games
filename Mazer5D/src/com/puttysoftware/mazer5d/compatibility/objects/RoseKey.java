/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericSingleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class RoseKey extends GenericSingleKey {
    // Constructors
    public RoseKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Key";
    }

    @Override
    public String getPluralName() {
        return "Rose Keys";
    }

    @Override
    public String getDescription() {
        return "Rose Keys will unlock Rose Locks, and can only be used once.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.ROSE_KEY;
    }
}