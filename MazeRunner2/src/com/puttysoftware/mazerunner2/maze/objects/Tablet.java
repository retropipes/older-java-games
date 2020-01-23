/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractInfiniteKey;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Tablet extends AbstractInfiniteKey {
    // Constructors
    public Tablet() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TABLET;
    }

    @Override
    public String getName() {
        return "Tablet";
    }

    @Override
    public String getPluralName() {
        return "Tablets";
    }

    @Override
    public String getDescription() {
        return "Tablets are used to fill Tablet Slots, and make them disappear. Tablets can be used infinitely many times.";
    }
}