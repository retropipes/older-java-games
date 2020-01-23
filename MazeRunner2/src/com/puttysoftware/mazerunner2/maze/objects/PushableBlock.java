/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMovableObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class PushableBlock extends AbstractMovableObject {
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