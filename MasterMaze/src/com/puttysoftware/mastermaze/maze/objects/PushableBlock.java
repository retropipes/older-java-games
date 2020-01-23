/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMovableObject;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class PushableBlock extends GenericMovableObject {
    // Constructors
    public PushableBlock() {
        super(true, false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_BLOCK);
    }

    @Override
    public String getName() {
        return "Pushable Block";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks can only be pushed, not pulled.";
    }
}