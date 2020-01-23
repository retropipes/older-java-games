/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericPass;

public class EnergySphere extends GenericPass {
    // Constructors
    public EnergySphere() {
        super();
    }

    @Override
    public String getName() {
        return "Energy Sphere";
    }

    @Override
    public String getPluralName() {
        return "Energy Spheres";
    }

    @Override
    public String getDescription() {
        return "Energy Spheres permit walking on Force Fields.";
    }
}
