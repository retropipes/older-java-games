/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericMarker;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MazeNoteObject extends GenericMarker {
    // Constructors
    public MazeNoteObject() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MAP_NOTE;
    }

    @Override
    public String getName() {
        return "Maze Note";
    }

    @Override
    public String getPluralName() {
        return "Maze Notes";
    }

    @Override
    public String getDescription() {
        return "";
    }
}