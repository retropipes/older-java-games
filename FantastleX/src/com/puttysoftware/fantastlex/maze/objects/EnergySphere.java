/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractPass;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class EnergySphere extends AbstractPass {
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
