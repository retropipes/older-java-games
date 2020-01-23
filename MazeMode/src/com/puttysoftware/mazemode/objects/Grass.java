/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericGround;

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
}