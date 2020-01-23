/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.maze.abc.AbstractWall;
import com.puttysoftware.ddremix.maze.utilities.TypeConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;

public class WallOn extends AbstractWall {
    // Constructors
    public WallOn() {
        super();
    }

    @Override
    public String getName() {
        return "Wall On";
    }

    @Override
    public String getPluralName() {
        return "Walls On";
    }

    @Override
    public String getDescription() {
        return "Walls On are impassable - you'll need to go around them.";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}