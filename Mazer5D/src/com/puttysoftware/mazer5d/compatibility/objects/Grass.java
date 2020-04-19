/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericGround;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Grass extends GenericGround {
    // Constructors
    public Grass() {
        super();
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public String getPluralName() {
        return "Squares of Grass";
    }

    @Override
    public String getDescription() {
        return "Grass is one of the many types of ground.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.GRASS;
    }
}