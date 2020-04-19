/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SeaweedWallOn extends GenericToggleWall {
    // Constructors
    public SeaweedWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall On";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls On";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls On can NOT be walked through, and will change to Seaweed Walls Off when a Seaweed Button is pressed.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SEAWEED_WALL_ON;
    }}