/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericProgrammableKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class MagentaCrystal extends GenericProgrammableKey {
    // Constructors
    public MagentaCrystal() {
        super("Magenta");
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MAGENTA_CRYSTAL;
    }
}