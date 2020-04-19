/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class PurpleWallOn extends GenericToggleWall {
    // Constructors
    public PurpleWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Wall On";
    }

    @Override
    public String getPluralName() {
        return "Purple Walls On";
    }

    @Override
    public String getDescription() {
        return "Purple Walls On can NOT be walked through, and will change to Purple Walls Off when a Purple Button is pressed.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.PURPLE_WALL_ON;
    }}