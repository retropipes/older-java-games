/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericGround;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Tundra extends GenericGround {
    // Constructors
    public Tundra() {
        super();
    }

    @Override
    public String getName() {
        return "Tundra";
    }

    @Override
    public String getPluralName() {
        return "Squares of Tundra";
    }

    @Override
    public String getDescription() {
        return "Tundra is one of the many types of ground.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.TUNDRA;
    }}