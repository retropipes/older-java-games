/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericTextHolder;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Sign extends GenericTextHolder {
    // Constructors
    public Sign() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SIGN;
    }

    @Override
    public String getName() {
        return "Sign";
    }

    @Override
    public String getPluralName() {
        return "Signs";
    }

    @Override
    public String getDescription() {
        return "Signs display their message when walked into.";
    }
}