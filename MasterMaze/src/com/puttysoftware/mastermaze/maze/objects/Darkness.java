/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericPassThroughObject;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Darkness extends GenericPassThroughObject {
    // Constructors
    public Darkness() {
        super(true, true, true, true);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DARKNESS;
    }

    @Override
    public String getName() {
        return "Darkness";
    }

    @Override
    public String getPluralName() {
        return "Squares of Darkness";
    }

    @Override
    public String getDescription() {
        return "Squares of Darkness are what fills areas that cannot be seen.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
        this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}