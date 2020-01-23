/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericGround;

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
}