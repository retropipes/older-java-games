/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericPass;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class EnergySphere extends GenericPass {
    // Constructors
    public EnergySphere() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_ENERGY_SPHERE;
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
