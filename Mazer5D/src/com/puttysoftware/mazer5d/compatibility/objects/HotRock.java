/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericGround;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class HotRock extends GenericGround {
    // Constructors
    public HotRock() {
        super();
    }

    @Override
    public String getName() {
        return "Hot Rock";
    }

    @Override
    public String getPluralName() {
        return "Squares of Hot Rock";
    }

    @Override
    public String getDescription() {
        return "Hot Rock is one of the many types of ground. It is created by Fire Amulets and Hot Boots, but can also exist on its own.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.HOT_ROCK;
    }}