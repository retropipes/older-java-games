/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.maze.abc.AbstractMarker;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;

public class MazeNoteObject extends AbstractMarker {
    // Constructors
    public MazeNoteObject() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NOTE;
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