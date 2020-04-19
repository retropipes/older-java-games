/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPlug;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class IPlug extends GenericPlug {
    // Constructors
    public IPlug() {
        super('I');
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.I_PLUG;
    }
}