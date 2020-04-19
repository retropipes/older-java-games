/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPassThroughObject;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class FakeWall extends GenericPassThroughObject {
    // Constructors
    public FakeWall() {
        super();
    }

    @Override
    public String getName() {
        return "Fake Wall";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Fake Walls";
    }

    @Override
    public String getDescription() {
        return "Fake Walls look like walls, but can be walked through.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.FAKE_WALL;
    }
}