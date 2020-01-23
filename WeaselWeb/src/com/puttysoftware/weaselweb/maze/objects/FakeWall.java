/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericPassThroughObject;

public class FakeWall extends GenericPassThroughObject {
    // Constructors
    public FakeWall() {
        super();
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Wall");
    }

    @Override
    public String getName() {
        return "Fake Wall";
    }

    @Override
    public String getPluralName() {
        return "Fake Walls";
    }

    @Override
    public String getDescription() {
        return "Fake Walls look like walls, but can be walked through.";
    }
}