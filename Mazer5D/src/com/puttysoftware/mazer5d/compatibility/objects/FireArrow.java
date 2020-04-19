/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTransientObject;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class FireArrow extends GenericTransientObject {
    // Constructors
    public FireArrow() {
        super("Fire Arrow");
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.FIRE_ARROW;
    }
}
