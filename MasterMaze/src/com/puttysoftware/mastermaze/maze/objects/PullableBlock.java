/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMovableObject;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class PullableBlock extends GenericMovableObject {
    // Constructors
    public PullableBlock() {
        super(false, true, ObjectImageConstants.OBJECT_IMAGE_PULLABLE);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Pullable Block";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks can only be pulled, not pushed.";
    }
}